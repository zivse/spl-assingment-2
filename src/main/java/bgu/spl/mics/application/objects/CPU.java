package bgu.spl.mics.application.objects;

import java.util.Vector;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */

public class CPU {
    private int cores; //number of cores
    private Vector<DataBatch> data; //the data the cpu currently procssing
    private Cluster cluster; //the compute
    private int beginningTime;
    private int time;
    private int alreadyProccesedDataTime;
    private int timeToProcessCurrentData;
    private boolean isActive;
    private DataBatch currentDataBatch;
    private int totalProcessDataAmount;

    private Object lock = new Object();
    public CPU(int _cores){
        totalProcessDataAmount=0;
        timeToProcessCurrentData =0;
        alreadyProccesedDataTime =0;
        cores=_cores;
        data= new Vector<>();
        cluster=cluster.getInstance();
        beginningTime=1;
        time=1;
        isActive=false;
        currentDataBatch=null;
    }
    //functions already processed data time
    public int getAlreadyProcessedDataTime() {
        return alreadyProccesedDataTime;
    }
    public void updateAlreadyProcessedDataTime(int incrementTime) {
        synchronized (lock) {
            alreadyProccesedDataTime = alreadyProccesedDataTime + incrementTime;
        }
    }
    //fuctions beginnng time
    public int getBeginningTime() {
        return beginningTime;
    }
    public void setBeginningTime() {
        isActive=true;
        beginningTime = time;
    }
    //functions time
    public int getTime() {
        return time;
    }
    public void updateTime() {
        time=time+1;
    }
    //functions CountOfDataToProcess
    public int getTimeToProcessCurrentData() {
        return timeToProcessCurrentData;
    }
    public void setTimeToProcessCurrentData(DataBatch dataToProcess) {
        Data.Type type = dataToProcess.getDataFromBath().getType();
        switch (type) {
            case Tabular: {
                timeToProcessCurrentData=(32/cores);
                break;
            }
            case Text: {
                timeToProcessCurrentData=(32/cores)*2;
                break;
            }
            case Images: {
                timeToProcessCurrentData=(32/cores)*4;
                break;
            }
        }
    }
//functions isActive
    public boolean getIsActive() {
    return isActive;
}
    public void setActive() {
        isActive=true;
    }

    //functions cluster
    public Cluster getCluster() {//why? there is only one cluster...
        return cluster;
    }

   //functions cores
   public int getCores(){
       return cores;
   }

   //add data batch to the vector data to process case cpu busy
    public void addDataBatch(DataBatch dataToProcess){
        data.add(dataToProcess) ;
    }

    //functions current data batch
    public DataBatch getCurrentDataBatch() {
        return currentDataBatch;
    }
    public void setCurrentDataBatch(DataBatch currentDataBatch) {
        this.currentDataBatch = currentDataBatch;
    }
    //updateCurrentDataToProcess update the current data from the vector to the next value
    public void updateCurrentDataToProcess(){
        if(!data.isEmpty()){
            currentDataBatch= data.remove(0);}
        else{
            currentDataBatch=null;
        }
    }
//total process data amount functions
    public int getTotalProcessDataAmount() {
        return totalProcessDataAmount;
    }
    public void incrementTotalProcessDataAmount() {
        totalProcessDataAmount = totalProcessDataAmount+ timeToProcessCurrentData;
    }
    public void decrementTotalProcessDataAmount(){
        totalProcessDataAmount = totalProcessDataAmount-timeToProcessCurrentData;
    }
}
