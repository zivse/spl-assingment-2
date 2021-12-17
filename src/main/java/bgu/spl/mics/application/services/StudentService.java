package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.objects.Student;

import java.util.Vector;
import java.util.concurrent.CountDownLatch;

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
    Model currentModel;
    int indexModel;
    public StudentService(Student student, CountDownLatch countDown) {
        super("StudentService",countDown);
        this.student=student;
        indexModel=0;
        currentModel=null;
    }

    @Override
    protected void initialize() {
        currentModel=student.getModelVector().get(0);
        subscribeBroadcast(PublishConferenceBroadcast.class,(PublishConferenceBroadcast event)->{
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
        subscribeBroadcast(TickBroadcast.class, (TickBroadcast c)-> {
            if(indexModel<student.getModelVector().size()) {
                if (currentModel.getStatus() == Model.Status.PreTrained) {
                    sendEvent(new TrainModelEvent(currentModel));
                    currentModel.setStatus(Model.Status.Training);
                } else if (currentModel.getStatus() == Model.Status.Trained) {
                    System.out.println("student service");
                    if (sendEvent(new TestModelEvent()).get() == "Good") {
                        sendEvent(new PublishResultsEvent(currentModel));
                    }
                    currentModel.setStatus(Model.Status.Tested);
                    indexModel = indexModel + 1;
                    currentModel = student.getModelVector().get(indexModel);
                }
            }
        });
        subscribeBroadcast(TerminateBroadcast.class,(TerminateBroadcast c)-> {
            terminate();
        });
    }}

//            for(Model currentModel:tempModelsVector){
//            if(sendEvent(new TrainModelEvent(currentModel)).get()!=null){
//                if(sendEvent(new TestModelEvent()).get()=="Good"){
//                    sendEvent(new PublishResultsEvent(currentModel));
//                };
//            }
//        }