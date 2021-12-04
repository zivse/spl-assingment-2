package bgu.spl.mics.application.objects;

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

    private int name;
    private String department;
    private Degree status;
    private int publications;
    private int papersRead;

    public Student(int _name, String _department, Degree _status){
        name = _name;
        department = _department;
        status = _status;
        publications = 0;
        papersRead = 0;
    }
}
