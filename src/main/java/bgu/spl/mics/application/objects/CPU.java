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
    private int tickCounter;
    private int totalCpuTime;
    private DataBatch currentDataBatch;
    private int timeToCompleteData;

    private Object lock = new Object();
    public CPU(int _cores){
        cores=_cores;
        data= new Vector<>();
        cluster=cluster.getInstance();
        tickCounter = 0;
        totalCpuTime = 0;
        currentDataBatch=null;
        timeToCompleteData = 0;
    }

    public int timeToProcessCurrentData(DataBatch dataToProcess) {
        Data.Type type = dataToProcess.getDataFromBath().getType();
        int timeToProcessCurrentData =0;
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
        return timeToProcessCurrentData;
    }
    public void processData(){
        if(currentDataBatch != null){
            totalCpuTime += 1;
            tickCounter +=1;
            if(tickCounter >= timeToProcessCurrentData(currentDataBatch)){
                cluster.sendDataToGpu(currentDataBatch);
                timeToCompleteData -= timeToProcessCurrentData(currentDataBatch);
                if(!data.isEmpty()){
                    currentDataBatch = data.remove(0);
                }
                else{
                    currentDataBatch = null;
                }
            }
        }
        else{
            if(!data.isEmpty()){
                currentDataBatch = data.remove(0);
            }
        }
    }

   //functions cores
   public int getCores(){
       return cores;
   }

   //add data batch to the vector data to process case cpu busy
    public void addDataBatch(DataBatch dataToProcess){
        data.add(dataToProcess);
        timeToCompleteData += timeToProcessCurrentData(dataToProcess);
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
    public int getTimeToCompleteData(){
        return timeToCompleteData;
    }
    public int getTotalCpuTime(){
        return totalCpuTime;
    }



}
