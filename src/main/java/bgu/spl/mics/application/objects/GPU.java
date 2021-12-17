package bgu.spl.mics.application.objects;
import bgu.spl.mics.Event;
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
    public void incrementMemoryBy1() {
        memory=memory+1;
    }

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
    private int memory;
    private Vector<DataBatch> dataToTrainVector;
    private DataBatch currentDataToTrain;
    private int timeToTrainEachData;
    private int time;
    int beginningTime;
    private boolean activeTrain;
    private DataBatch gpuCurrentDataBatch;
    public GPU(String _type){
        trainModelEvent=null;
        gpuCurrentDataBatch=null;
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

    public void setGpuCurrentDataBatch(DataBatch gpuCurrentDataBatch) {
        this.gpuCurrentDataBatch = gpuCurrentDataBatch;
    }

    public DataBatch getCurrentDataToTrain() {
        return currentDataToTrain;
    }

    public void updateAlreadyTrainedData() {
        this.alreadyTrainedDataTime = alreadyTrainedDataTime+timeToTrainEachData;
    }

    public boolean getIsActiveTrain() {
        return activeTrain;
    }

    public void setActiveTrain(boolean activeTrain) {
        this.activeTrain = activeTrain;
    }

    public int getBeginningTime() {
        return beginningTime;
    }

    public int getTimeToTrainEachData() {
        return timeToTrainEachData;
    }

    public int getTime() {
        return time;
    }
    public void setBeginningTime(int beginningTime) {
        this.beginningTime = beginningTime;
    }

    public void updateTime(){
        time=time+1;
    }
    public void setModel(Model currentModel){ //set the model of the gpu to new model to work on.
        model=currentModel;
        indexCurrentData=0;
    }
    public void addData(DataBatch dataToTrain) {
        dataToTrainVector.add(dataToTrain);
    }
    public DataBatch splitData() {
        while (memory > 0 && indexCurrentData < model.getData().getSize()) {
            int tempIndexCurrentData = indexCurrentData;
            indexCurrentData = indexCurrentData + 1000;
            DataBatch dataToProcess = new DataBatch(model.getData(), tempIndexCurrentData, this);
            cluster.processData(dataToProcess);
            memory = memory - 1;
            return dataToProcess;
        }
        return null;
    }
    public void updateCurrentDataToTrain(){
        dataToTrainVector.remove(0);
        gpuCurrentDataBatch=dataToTrainVector.get(0);
    }
    public void setModel(String result){
        model.setResults(result);
    }
    public String getStudentFromGPU(){
        return model.getStudentDegree();
    }
public boolean getIsFinishedTrained(){
        int size=model.getData().getSize();;
        int timePerDataTrain= timeToTrainEachData;
        return ((size/1000)*timePerDataTrain==alreadyTrainedDataTime);
}
    public int getMemory() {
        return memory;
    }

    public void setTrainModelEvent(Event trainModelEvent) {
        this.trainModelEvent = trainModelEvent;
    }

    public Event getTrainModelEvent() {
        return trainModelEvent;
    }

    public int getAlreadyTrainedDataTime() {
        return alreadyTrainedDataTime;
    }
}