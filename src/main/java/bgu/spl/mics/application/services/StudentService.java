package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.PublishResultsEvent;
import bgu.spl.mics.TestModelEvent;
import bgu.spl.mics.TrainModelEvent;
import bgu.spl.mics.application.objects.Student;

/**
 * Student is responsible for sending the {@link TrainModelEvent},
 * {@link TestModelEvent} and {@link PublishResultsEvent}.
 * In addition, it must sign up for the conference publication broadcasts.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class StudentService extends MicroService {
    private Student student;
    public StudentService(Student student) {
        super("StudentService");
        this.student=student;
    }

    @Override
    protected void initialize() {
        sendEvent(new PublishResultsEvent());


    }
}
