package bgu.spl.mics.application.objects;

import java.util.LinkedList;
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
    private Vector<Model> studentModels; //check if legal adding this to the constructor.

    public Student(String _name, String _department, String _status,Vector<Model> _studentModels ){
        name = _name;
        department = _department;
        status = Degree.valueOf("_status");
        publications = 0;
        papersRead = 0;
        studentModels = _studentModels;
    }
}
