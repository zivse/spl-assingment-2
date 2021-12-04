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
    public GPU(Type _type){
        type = _type;
        model = null;
        cluster=cluster.getInstance();
    }

    public boolean train(){
        return true;
    }
    public boolean test(){
        return true;
    }

}
