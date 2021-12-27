package bgu.spl.mics.application.objects;
/**
 * Passive object representing a Deep Learning model.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Model {
   public enum Status {
        PreTrained, Training, Trained, Tested
    }
    public enum Results{
        None, Good, Bad
    }
    private String name;
    private Data data;
    private Student student;
    private Status status;
    private Results results;
    // private int size;
    public Model(String _name, Data _data,Student _student){ //check if delete student - Student _student
        name=_name;
        data=_data;
        student=_student;
        status = Status.PreTrained;
        results = Results.None;


    }
    public boolean isDone(){
        if(data.getSize() == data.getProcessed()){
            return true;
        }
        return false;
    }
    public Results getResults(){
        return results;
    }
    public void setResults(Results _results){
        this.results = _results;
    }

    public Status getStatus(){
        return status;
    }
    public void setStatus(Status _status){
        status = _status;
    }
    public String getName(){
        return name;
    }
    public Data getData(){
        return data;
    }
    public Student getStudent(){
        return student;
    }
    public String toString(){
        String modelString = "";
        modelString += "name: " + name+ "\n" + "data: " + data.toString() + "\n" + "status: " + status + "\n" + "results: " + results;
        return modelString;
    }
}