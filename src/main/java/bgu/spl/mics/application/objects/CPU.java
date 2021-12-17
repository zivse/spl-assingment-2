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

    private Object lock = new Object();
    public CPU(int _cores){
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
    public void setBeginningTime(int beginningTime) {
        isActive=true;
        this.beginningTime = beginningTime;
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
        int countOfData=0;
        Data.Type type = dataToProcess.getDataFromBath().getType();
        switch (type) {
            case Tabular: {
                countOfData=(32/cores);
            }
            case Text: {
                countOfData=(32/cores)*2;
            }
            case Images: {
                countOfData=(32/cores)*4;
            }
        }
        this.timeToProcessCurrentData =this.timeToProcessCurrentData + countOfData;
    }






    public Cluster getCluster() {//why? there is only one cluster...
        return cluster;
    }

    public DataBatch getCurrentDataBatch() {
        return currentDataBatch;
    }

    public boolean getIsActive() {
        return isActive;
    }
    public int getCores(){
        return cores;
    }

    public void setActive() {
        isActive=true;
    }
    public void setCurrentDataBatch(DataBatch currentDataBatch) {
        this.currentDataBatch = currentDataBatch;
    }
    public void addDataBatch(DataBatch dataToProcess){
        data.add(dataToProcess) ;
    }
    public void updateCurrentDataToProcess(){
        if(!data.isEmpty()){
        currentDataBatch= data.remove(0);}
        else{
            currentDataBatch=null;
        }
    }
}
