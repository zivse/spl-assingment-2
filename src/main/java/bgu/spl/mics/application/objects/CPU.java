package bgu.spl.mics.application.objects;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */

public class CPU {
    private int cores; //number of cores
    private LinkedList<DataBatch> data; //the data the cpu currently procssing
    private Cluster cluster; //the compute
    int beginningTime;
    public CPU(int _cores){
        cores=_cores;
        data= new LinkedList<DataBatch>();
        cluster=cluster.getInstance();
        beginningTime=0;
    }
    /**
    @pre none
    @post beginningTime=@pre beginningTime+1
     */
    public void updateTime(){
        beginningTime=beginningTime++;
    }

    /**
     *
     * @return the field beginningTime
     * @pre none
     * @post none
     */
    public int getTime(){
        return beginningTime;
    }

    /**
     *
     * @param unProcess
     * @return the process data
     * @pre unProcess!=null
     * @post Process=@param unProcess
     */
    public DataBatch ProcessData(DataBatch unProcess){ //process the data and return it processed
        DataBatch Process=unProcess;
       return Process;
    }

    /**
     *
     * @param unProcess
     * @pre unProcess!=null
     * @post isAddedData(@param unProcess)==true
     */
    public void addUnProcessedData(DataBatch unProcess){
      data.add(unProcess);
    }

    /**
     *
     * @param unProcess
     * @return true if data contains unProcess
     * @pre none
     * @post none
     */
    public boolean isAddedData(DataBatch unProcess){
        return data.contains(unProcess);
    }

}
