package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.objects.Student;

import java.util.Vector;

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
        subscribeBroadcast(PublishConferenceBroadcast.class,event->{
        if(event.getConnectStudentToArticles().get(student)==null){
            student.setPapersRead(event.getTotalPublishers());
        }
        else{
            Vector<String> models=event.getConnectStudentToArticles().get(student);
            int modelsSize=models.size();
            student.setPublications(modelsSize);
            student.setPapersRead(event.getTotalPublishers()-modelsSize);
        }
        });
        subscribeBroadcast(TerminateBroadcast.class, new Callback<TerminateBroadcast>() {
            @Override
            public void call(TerminateBroadcast c) {
                terminate();
            }
        });
        Vector<Model> tempModelsVector=student.getModelVector();

             for(Model currentModel:tempModelsVector){
            if(sendEvent(new TrainModelEvent(currentModel)).get()!=null){
                if(sendEvent(new TestModelEvent()).get()=="Good"){
                    sendEvent(new PublishResultsEvent(currentModel));
                };
            }
        }

    }
}
