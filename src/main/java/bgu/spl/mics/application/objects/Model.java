package bgu.spl.mics.application.objects;

/**
 * Passive object representing a Deep Learning model.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Model {
    enum Status {
        PreTrained, Training, Trained, Tested
    }
    enum Results{
        None, Good, Bad
    }
    private String name;
    private Data data;
    private Student student;
    private Status status;
    private Results results;
public Model(String _name, Data _data,Student _student){ //check if delete student
    name=_name;
    data=_data;
    student=_student;
    status = Status.PreTrained;
    results = Results.None;


}
    public Results getRsults(){
        return results;

    }
    public void setResults(Results _results){
        results = _results;
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
}

