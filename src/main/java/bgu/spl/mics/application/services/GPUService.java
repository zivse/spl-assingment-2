package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.objects.GPU;

import java.util.Timer;
import java.util.TimerTask;

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
    public GPUService(String name,GPU _gpu) {
        super(name);
        gpu=_gpu;
    }

    @Override
    protected void initialize() {
        subscribeBroadcast(TickBroadcast.class, new Callback<TickBroadcast>() {
            @Override
            public void call(TickBroadcast c) {
                gpu.updateTime();
              };
            });
        subscribeEvent(TestModelEvent.class, new Callback<TestModelEvent>() {
                    @Override
                    public void call(TestModelEvent testModelEvent) {
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

                    }});
        subscribeEvent(TrainModelEvent.class, new Callback<TrainModelEvent>() {
            @Override
            public void call(TrainModelEvent c) {
                gpu.setModel(c.);
              gpu.splitData();
            }
        });
                //  subscribeBroadcast(TerminateBroadcast.class, (TerminateBroadcast broadcastTerminate) -> this.terminate()); //for closing
    }
}
