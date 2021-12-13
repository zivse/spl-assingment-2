package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.TestModelEvent;
import bgu.spl.mics.TickBroadcast;
import bgu.spl.mics.TrainModelEvent;
import bgu.spl.mics.application.objects.GPU;

import java.util.Timer;
import java.util.TimerTask;

/**
 * GPU service is responsible for handling the
 * {@link TrainModelEvent} and {@link TestModelEvent},
 * in addition to sending the {@link DataPreProcessEvent}.
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
        subscribeBroadcast(TickBroadcast.class, callBack -> {
            gpu.updateTime();
        });
        subscribeEvent(TestModelEvent.class,callBack->{

        });
    }
}
