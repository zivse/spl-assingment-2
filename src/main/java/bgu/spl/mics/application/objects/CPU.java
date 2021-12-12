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
    public Boolean proccessData(DataBatch dataBatch){
        Data.Type type = dataBatch.getDataFromBath().getType();
        switch (type){
            case Tabular:{
                for(int i=0; i< 1*(32/cores); i++){
                    try{
                        this.wait();
                    }
                    catch (InterruptedException ignored){
                    }
                }
                break;
            }
            case Text: {
                for (int i = 0; i < 2 * (32 / cores); i++) {
                    try {
                        this.wait();
                    } catch (InterruptedException ignored) {
                    }
                }
                break;

            }
            case Images:{
                for(int i=0; i< 4*(32/cores); i++){
                    try{
                        this.wait();
                    }
                    catch (InterruptedException ignored){
                    }
                }
                break;
            }
        }
        cluster.trainData(dataBatch);
        return true;
    }


}
