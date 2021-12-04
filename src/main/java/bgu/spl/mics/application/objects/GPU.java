package bgu.spl.mics.application.objects;

/**
 * Passive object representing a single GPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class GPU {
    /**
     * Enum representing the type of the GPU.
     */
    enum Type {RTX3090, RTX2080, GTX1080}

    private Type type;
    private Model model;
    Cluster cluster;
    Data unProcessData;
    public GPU(Type _type){
        type = _type;
        model = null;
        cluster=cluster.getInstance();
        unProcessData=new Data();
    }

    /**
     *
     * @return divided data
     * @pre none
     * @post isSmaller1000(@param a)==true
     */
    public DataBatch divideData(){
    DataBatch a=new DataBatch();
return a;
}
public boolean isSmaller1000(DataBatch a){
        return(a.getSize()<1000);
}

    /**
     *
     * @param data
     * @pre data!=null
     * @post unProcessData=@param data
     */
    public void getDataFromCluster(Data data){
    unProcessData= data;
}
public Data getData(){
        return unProcessData;
}

    /**
     *
     * @return true if train finished
     * @pre none
     * @post none
     */
    public boolean trainData(){
        return true;
}

}
