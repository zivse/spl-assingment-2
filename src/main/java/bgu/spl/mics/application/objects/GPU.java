package bgu.spl.mics.application.objects;
import bgu.spl.mics.TrainModelEvent;

import java.util.Vector;

/**
 * Passive object representing a single GPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class GPU {
    /**
     * Enum representing the type of the GPU.
     */

    enum Type {RTX3090, RTX2080, GTX1080}

    private TrainModelEvent currEvent;
    private Type type;
    private Model currentModelToTrain;//the model the gpu is currently working on
    private Cluster cluster;
    private int tickCounter;
    private int totalGPUTime;
    private Vector<DataBatch> dataToTrainVector;
    private Vector<Model> modelToTrain;
    private Vector<Model> modelToTest;
    private DataBatch currentDataToTrain;
    private int timeToTrainEachData;
    private int timeToProccessData;

    //constructor
    public GPU(String _type) {
        currentDataToTrain = null;
        currentModelToTrain = null;
        cluster = cluster.getInstance();
        dataToTrainVector = new Vector<DataBatch>();
        if (_type.compareTo("RTX3090") == 0) {
            dataToTrainVector.setSize(32);
            type = Type.RTX3090;
            timeToProccessData = 1;
        } else if (_type.compareTo("RTX2080") == 0) {
            dataToTrainVector.setSize(16);
            type = Type.RTX2080;
            timeToProccessData = 2;
        } else {
            dataToTrainVector.setSize(8);
            type = Type.GTX1080;
            timeToProccessData = 4;
        }
        tickCounter = 0;
        totalGPUTime = 0;
        modelToTrain = new Vector<>();
        modelToTest = new Vector<>();
    }

    //train
    public void train(TrainModelEvent trainModelEvent){
        if (currentModelToTrain == null){
            currentModelToTrain = trainModelEvent.getModel();
            currentModelToTrain.setStatus(Model.Status.Training);
            for(int i=0;i<dataToTrainVector.size();i++){//split the data
                cluster.processData(currentModelToTrain.getData().split(this));
            }
        }
        else{
            modelToTrain.add(trainModelEvent.getModel());
        }
    }

    //check if finish training dataBatch
    public void trainDataBatch() {
        if (currentModelToTrain == null) {
            if (!modelToTest.isEmpty()) {
                testModel(modelToTest.remove(0));
            }
            if (!modelToTrain.isEmpty()) {
                currentModelToTrain = modelToTrain.remove(0);
                currentModelToTrain.setStatus(Model.Status.Training);
                tickCounter = 0;
            }
            return;
        }
        if (currentModelToTrain.getStatus().equals(Model.Status.Training)){
                if (currentDataToTrain != null) {
                    tickCounter += 1;
                    totalGPUTime += 1;
                    if (tickCounter >= timeToProccessData) {
                        currentModelToTrain.getData().setProcessed();
                        tickCounter = 0;
                        if (!dataToTrainVector.isEmpty()) {
                            currentDataToTrain = dataToTrainVector.remove(0);
                            if (!currentModelToTrain.isDone()) {
                                cluster.processData(currentModelToTrain.getData().split(this));
                                }
                            }
                            else{
                                currentDataToTrain = null;
                            }
                        }
                    }
                else {
                    if (!dataToTrainVector.isEmpty()) {
                        currentDataToTrain = dataToTrainVector.remove(0);
                    }
                }
            }
         if(currentModelToTrain.getData().getProcessed() == currentModelToTrain.getData().getSize()){
            currentModelToTrain.setStatus(Model.Status.Trained);
            currentModelToTrain = null;
            if(!modelToTest.isEmpty()){
                testModel(modelToTest.remove(0));
            }
            if(!modelToTrain.isEmpty()){
                currentModelToTrain = modelToTrain.remove(0);
                currentModelToTrain.setStatus(Model.Status.Training);
            }
        }
    }

    //test model
    public void testModel(Model model) {
        if(model == null){
            return;
        }
        model.setStatus(Model.Status.Tested);
        int range = 10 + 1;
        int prob = (int) (Math.random() * range);
        Student.Degree studentDegree = model.getStudent().getDegree();
        if (studentDegree.equals(Student.Degree.MSc)) {
            if (prob <= 6) {
                model.setResults(Model.Results.Good);
            } else {
                model.setResults(Model.Results.Bad);
            }
        }
        else {
            if (prob <= 8) {
                model.setResults(Model.Results.Good);
            } else {
                model.setResults(Model.Results.Bad);
            }
        }
        if(!modelToTest.isEmpty()){
            testModel(modelToTest.remove(0));
        }
    }

    public void addDataBatchToTrain(DataBatch dataBatch){
        dataToTrainVector.add(dataBatch);
    }
    public int getTotalGPUTime(){
        return totalGPUTime;
    }
}