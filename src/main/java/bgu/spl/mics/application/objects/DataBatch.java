package bgu.spl.mics.application.objects;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */

public class DataBatch {
    private int index;
    private Data data;
    public DataBatch(int _index, Data _data){
        index = _index;
        data = _data;
    }
    public Data getDataFromBath(){
        return data;
    }
}
