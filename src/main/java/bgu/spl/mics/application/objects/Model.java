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
    // private int size;
    public Model(String _name, Data _data,Student _student){ //check if delete student - Student _student
        name=_name;
        data=_data;
        //size = _size;
        student=_student;
        status = Status.PreTrained;
        results = Results.None;


    }
    public Results getResults(){
        return results;

    }
    public String getStudentDegree(){
        return student.getDegree();
    }
    public void setResults(String result){
    if(result.compareTo("Good")==0){
       results=Results.Good;
    }
    else{
        results=Results.Bad;
    }
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