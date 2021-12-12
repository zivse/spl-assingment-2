package bgu.spl.mics.application.objects;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */

public class DataBatch {
    private int start_index;
    private Data data;
    private GPU gpu;
    public DataBatch(Data _data,int _start_index,GPU _gpu){
        start_index = _start_index;
        data = _data;
        gpu=_gpu;
    }
    public Data getDataFromBath(){
        return data;
    }
    public GPU getGPU(){
        return gpu;
    }
}
