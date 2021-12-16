package bgu.spl.mics.application.objects;

import java.util.Collection;
import java.util.LinkedList;
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
    int beginningTime;
    int time;
    int countOfDataToProcess;
    boolean isActive;
    DataBatch currentDataBatch;
    int alreadyTrainedDataTime;
    public CPU(int _cores){
        countOfDataToProcess=0;
        alreadyTrainedDataTime=0;
        cores=_cores;
        data= new Vector<>();
        cluster=cluster.getInstance();
        beginningTime=1;
        time=1;
        isActive=false;
        currentDataBatch=null;
    }

    public int getCountOfDataToProcess() {
        return countOfDataToProcess;
    }

    public void setCountOfDataToProcess(DataBatch dataToProcess) {
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
        this.countOfDataToProcess =this.countOfDataToProcess+ countOfData;
    }

    public int getAlreadyTrainedDataTime() {
        return alreadyTrainedDataTime;
    }
    public Cluster getCluster() {//why? there is only one cluster...
        return cluster;
    }

    public DataBatch getCurrentDataBatch() {
        return currentDataBatch;
    }

    public int getBeginningTime() {
        return beginningTime;
    }

    public boolean getIsActive() {
        return isActive;
    }
    public int getCores(){
        return cores;
    }

    public int getTime() {
        return time;
    }

    public void setActive() {
        isActive=true;
    }

    public void setBeginningTime(int beginningTime) {
        isActive=true;
        this.beginningTime = beginningTime;
    }

    public void updateTime() {
        time=time++;
    }

    public void setCurrentDataBatch(DataBatch currentDataBatch) {
        this.currentDataBatch = currentDataBatch;
    }
    public void addDataBatch(DataBatch dataToProcess){
        data.add(dataToProcess) ;
    }
    public void updateCurrentDataToProcess(){
        data.remove(0);
        currentDataBatch=data.get(0);
    }
}



//    public Boolean proccessData(DataBatch dataBatch){
//        Data.Type type = dataBatch.getDataFromBath().getType();
//        switch (type){
//            case Tabular:{
//                for(int i=0; i< 1*(32/cores); i++){
//                    try{
//                        this.wait();
//                    }
//                    catch (InterruptedException ignored){
//                    }
//                }
//                break;
//            }
//            case Text: {
//                for (int i = 0; i < 2 * (32 / cores); i++) {
//                    try {
//                        this.wait();
//                    } catch (InterruptedException ignored) {
//                    }
//                }
//                break;
//
//            }
//            case Images:{
//                for(int i=0; i< 4*(32/cores); i++){
//                    try{
//                        this.wait();
//                    }
//                    catch (InterruptedException ignored){
//                    }
//                }
//                break;
//            }
//        }
//        cluster.trainData(dataBatch);
//        return true;
//    }
