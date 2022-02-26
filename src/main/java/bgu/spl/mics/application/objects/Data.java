package bgu.spl.mics.application.objects;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Data {
    /**
     * Enum representing the Data type.
     */
    public enum Type {
        Images, Text, Tabular
    }

    private Type type;
    private int processed;
    private int size;
    private int index;

    public Data(String _type, int _size){
        if(_type.compareTo("Text")==0){
            type = type.Text;
        }
        else if(_type.compareTo("Images")==0){
            type = type.Images;
        }
        else{
            type = type.Tabular;
        }
        processed = 0;
        size = _size;
        index = 0;

    }
    public Type getType(){
        return type;
    }

    public String toString(){
        String dataString = "";
        dataString += "type: " + type + "\n" + "size: " + size +"\n";
        return dataString;
    }

    public int getSize() {
        return size;
    }

    public void  setProcessed(){
        processed += 1000;
    }
    public int getProcessed(){
        return processed;
    }
    public synchronized DataBatch split(GPU gpu){
        DataBatch dataBatch = new DataBatch(this, index, gpu);
        index += 1000;
        return dataBatch;
    }
}
