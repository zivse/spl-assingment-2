package bgu.spl.mics.application.objects;
import bgu.spl.mics.Event;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive object representing a single GPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class GPU {
    /**
     * Enum representing the type of the GPU.
     */
    Event trainModelEvent;
    enum Type {RTX3090, RTX2080, GTX1080}
    private Type type;
    private Model model;//the model the gpu is currently working on
    private Cluster cluster;
    private int alreadyTrainedDataTime;
    private int indexCurrentData;
    private AtomicInteger memory;
    private Vector<DataBatch> dataToTrainVector;
    private DataBatch currentDataToTrain;
    private int timeToTrainEachData;
    private int time;
    int beginningTime;
    private boolean activeTrain;
    //constructor
    public GPU(String _type){
        trainModelEvent=null;
        activeTrain=false;
        currentDataToTrain=null;
        beginningTime=1;
        alreadyTrainedDataTime=0;
        model = null;
        cluster=cluster.getInstance();
        indexCurrentData=0;
        dataToTrainVector=new Vector<DataBatch>();
        if(_type.compareTo("RTX3090")==0){
            type = Type.RTX3090;
        }
        else if(_type.compareTo("RTX2080")==0){
            type = Type.RTX2080;
        }
        else{
            type = Type.GTX1080;
        }
        if(_type.compareTo("RTX3090")==0){
            memory=new AtomicInteger(32);
            timeToTrainEachData=1;
        }
        else if(_type.compareTo("RTX2080")==0){
            memory=new AtomicInteger(16);
            timeToTrainEachData=2;
        }
        else{
            memory=new AtomicInteger(8);
            timeToTrainEachData=4;
        }
        time=1;
    }
   //fuctions current data to train
   public DataBatch getCurrentDataToTrain() {
       return currentDataToTrain;
   }
    public void updateCurrentDataToTrain(){
        try {
            currentDataToTrain = dataToTrainVector.remove(0);
        }catch(ArrayIndexOutOfBoundsException e){
            currentDataToTrain =null; };
    }
    public void addDataToTrainToVector(DataBatch dataToTrain) { //if gpu busy add data to vector
        dataToTrainVector.add(dataToTrain);
    }
    public void setCurrentDataToTrain(DataBatch current){
        currentDataToTrain=current;
    }
    //already trained data
    public int getAlreadyTrainedDataTime() {
        return alreadyTrainedDataTime;
    }
    public void updateAlreadyTrainedData() {
        this.alreadyTrainedDataTime = alreadyTrainedDataTime+1000;
    }
    //beginning time functions
    public int getBeginningTime() {
        return beginningTime;
    }
    public void setBeginningTime() {
        this.beginningTime = time;
    }
    //active train function
    public boolean getIsActiveTrain() {
        return activeTrain;
    }
    public void setActiveTrain(boolean activeTrain) {
        this.activeTrain = activeTrain;
    }
    //fuctions time to train each data culculate already in constructor
    public int getTimeToTrainEachData() {
        return timeToTrainEachData;
    }
    //functions time
    public int getTime() {
        return time;
    }
    public void updateTime(){
        time=time+1;
    }
    //index current data metods
    public int getIndexCurrentData(){
        return indexCurrentData;
    }
    public void updateIndexCurrentData() {
        indexCurrentData = indexCurrentData+1000;
    }
//functions model
public Model getModel(){
    return model;
}
public int getModelDataSize(){
        return this.model.getData().getSize();
}
public void setModelAndInitializeIndexCurrentData(Model currentModel){ //set the model of the gpu to new model to work on.
    model=currentModel;
    indexCurrentData=0;
}
    public void setModelResults(String result){
        model.setResults(result);
    }
    public void setModelStatus(Model.Status status){
        model.setStatus(status);
    }
//functions memory
    public int getMemory() {
    return memory.intValue();
}
    public void incrementMemoryBy1() {
        memory.incrementAndGet();
    }
    public void decreaseMemoryBy1(){
        memory.decrementAndGet();
    }
//functions model event
public void setTrainModelEvent(Event trainModelEvent) {
    this.trainModelEvent = trainModelEvent;
}

    public Event getTrainModelEvent() {
        return trainModelEvent;
    }


    //split data function
    public void splitData() {
        int tempIndexCurrentData = indexCurrentData;
        updateIndexCurrentData();
        DataBatch dataToProcess = new DataBatch(model.getData(), tempIndexCurrentData, this);
        decreaseMemoryBy1();
        cluster.processData(dataToProcess);
    }
//student function
    public String getStudentDegreeFromGPU(){
        return model.getStudentDegree();
    }
//is finish trained
public boolean getIsFinishedTrained(){
        return (alreadyTrainedDataTime == getModelDataSize());
}
}