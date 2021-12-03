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
    public CPU(int _cores){
        cores=_cores;
        data= new LinkedList<DataBatch>();
        cluster=cluster.getInstance();
    }
    public void addData(DataBatch anotherData){
        data.add(anotherData);

    }

    public void imageProcessData(DataBatch DataToProcess){ //culculate time for image
    }
    public void textProcessData(DataBatch DataToProcess){ //culculate time for text

    }
    public void TabularProcessData(DataBatch DataToProcess){ ////culculate time for tabular

    }


}
