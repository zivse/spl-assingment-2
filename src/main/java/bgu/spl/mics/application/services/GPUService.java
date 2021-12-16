package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.objects.GPU;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/**
 * GPU service is responsible for handling the
 * {@link TrainModelEvent} and {@link TestModelEvent},
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class GPUService extends MicroService {
private GPU gpu;
    public GPUService(String name, GPU _gpu, CountDownLatch countDown) {
        super(name,countDown);
        gpu=_gpu;
    }

    @Override
    protected void initialize() {
        subscribeEvent(TestModelEvent.class, (TestModelEvent testModelEvent)-> {
                        int range = 10+1;
                        int prob= (int)(Math.random()*range);
                        String typeStudent=gpu.getStudentFromGPU();
                        String result="";
                        if(typeStudent.compareTo("MSc")==0){
                            if(prob<=6){
                                result="Good";
                                gpu.setModel("Good");
                            }
                            else{
                                result="Bad";
                                gpu.setModel("Bad");
                            }
                        }
                        else{
                            if(prob<=8){
                                result= "Good";
                                gpu.setModel("Good");
                            }
                            else{
                                result="Bad";
                                gpu.setModel("Bad");
                            }
                        }
                        complete(testModelEvent,result);

                    });
        subscribeEvent(TrainModelEvent.class, (TrainModelEvent c)-> {
              gpu.setModel(c.getModel());
              gpu.splitData();
        });
        subscribeBroadcast(TerminateBroadcast.class,(TerminateBroadcast c)-> {
                terminate();
        });
    }
}
