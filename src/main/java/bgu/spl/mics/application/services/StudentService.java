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
    private Model currentModel;
    private int indexModel;
    private int currentEvent;
    public StudentService(Student student, CountDownLatch countDown) {
        super("StudentService",countDown);
        this.student=student;
        indexModel=0;
        currentModel=null;
        currentEvent = 0;
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
                if (currentModel.getStatus() == Model.Status.PreTrained && currentEvent == 0) {
                    TrainModelEvent eTrain=new TrainModelEvent(currentModel);
                    eTrain.setFuture(sendEvent(eTrain));
                    currentEvent += 1;
                }
                else if (currentModel.getStatus() == Model.Status.Trained&& currentEvent ==1) {
                    currentModel.setStatus(Model.Status.Tested);
                    TestModelEvent eTest=new TestModelEvent(currentModel);
                    eTest.setFuture(sendEvent(eTest));
                    currentEvent += 1;
                }
                else if(currentModel.getStatus() == Model.Status.Tested && currentEvent == 2){
                    currentEvent = 0;
                    System.out.println("student service 2");
                    indexModel = indexModel + 1;
                    if (currentModel.getResults() == Model.Results.Good) {
                        PublishResultsEvent ePublish = new PublishResultsEvent(currentModel);
                        ePublish.setFuture(sendEvent(ePublish));
                    }
                    System.out.println("model name" + currentModel.getName());
                    if(indexModel<student.getModelVector().size()){
                    currentModel = student.getModelVector().get(indexModel);
                        System.out.println("model name 2 " + currentModel.getName());
                     }
                }
            }
        });
        subscribeBroadcast(TerminateBroadcast.class,(TerminateBroadcast c)-> {
            terminate();
        });
    }}