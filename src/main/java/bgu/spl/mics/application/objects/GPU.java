package bgu.spl.mics.application.objects;
import bgu.spl.mics.Message;
import bgu.spl.mics.MicroService;
import java.net.Proxy;
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
    private Type type;
    private Model model;//the model the gpu is currently working on
    private Cluster cluster;
    private int alreadyTrainedData;
    private int indexCurrentData;
    private int memory;
    private Vector<DataBatch> dataToTrainVector;
    private int timeToTrainEachData;
    private int time;
    public GPU(String _type){
        if(_type.compareTo("RTX3090")==0){
            type = Type.RTX3090;
        }
        else if(_type.compareTo("RTX2080")==0){
            type = Type.RTX2080;
        }
        else{
            type = Type.GTX1080;
        }
        alreadyTrainedData=0;
        model = null;
        cluster=cluster.getInstance();
        indexCurrentData=0;
        dataToTrainVector=new Vector<DataBatch>();
        if(_type.compareTo("RTX3090")==0){
            memory=32;
            timeToTrainEachData=1;
        }
        else if(_type.compareTo("RTX2080")==0){
            memory=16;
            timeToTrainEachData=2;
        }
        else{
            memory=8;
            timeToTrainEachData=4;
        }
        time=1;
    }
    public void updateTime(){
        time=time+1;
    }
    public void setModel(Model currentModel){ //set the model of the gpu to new model to work on.
        model=currentModel;
        indexCurrentData=0;
    }
    public int getIndexCurrentData(){

        return indexCurrentData;
    }
    public DataBatch splitData(MicroService m){
        int tempIndexCurrentData=indexCurrentData;
        indexCurrentData=indexCurrentData+1000;
        DataBatch dataToProcess=new DataBatch(model.getData(),tempIndexCurrentData,this);
        cluster.processData(dataToProcess);
        return dataToProcess;
    }
    public void addData(DataBatch dataToTrain) {
        dataToTrainVector.add(dataToTrain);
    }
    public void trainDataLoop(MicroService m) throws InterruptedException {
        while (true) {
            DataBatch dataToTrain;
            try {
                dataToTrain = dataToTrainVector.remove(0);
            } catch (ArrayIndexOutOfBoundsException ignore) {
                dataToTrain = null;
            }
            while (dataToTrain == null) {
                m.wait();
            }
            trainData(dataToTrain,m);
        }
    }
    public void trainData(DataBatch dataToTrain,MicroService m) throws InterruptedException {
        int timeForTraining=timeToTrainEachData;
        while(timeForTraining>0){
            m.wait();
            timeForTraining--;
        }
    }
    public void test(){

    }
    public void initializeWork(Model currentModel){
        setModel(currentModel);
    }

}
