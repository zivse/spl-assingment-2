package bgu.spl.mics.application.objects;

import java.util.Vector;

/**
 * Passive object representing single student.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Student {
    /**
     * Enum representing the Degree the student is studying for.
     */
    enum Degree {
        MSc, PhD
    }

    private String name;
    private String department;
    private Degree status;
    private int publications;
    private int papersRead;
    Vector<String>goodArticles;
    private Vector<Model> modelsVector;

    public Student(String _name, String _department, String _status,Vector<Model> _modelsVector){
        goodArticles=new Vector<String>();
        name = _name;
        department = _department;
        modelsVector = _modelsVector;
        if(_status.compareTo("MSc")==0){
            status = Degree.MSc;
        }
        else{
            status = Degree.PhD;
        }
        publications = 0;
        papersRead = 0;
    }

    public void setModelsVector(Vector<Model> _modelsVector) {
        modelsVector = _modelsVector;
    }
    public Degree getDegree(){
       return status;
    }
    public Vector<Model> getModelVector(){
        return modelsVector;
    }

    public String toString(){
        String studentString = "";
        studentString  = studentString+ "name: " + name + "\n" + "department: " + department + "\n" + "status: " + status + "\n" + "publications" + publications + "\n" + "papersRead: " + papersRead +"\n" + "trainedModels: " + "\n";
        for(Model model : modelsVector){
            studentString += model.toString();
        }
        return studentString;

    }
}