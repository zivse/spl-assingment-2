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

    public Data(String _type, int _size){ //fix all gpu related
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

    }
    public Type getType(){
        return type;
    }

}
