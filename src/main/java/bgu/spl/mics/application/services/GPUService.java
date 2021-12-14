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
        subscribeBroadcast(TickBroadcast.class, (TickBroadcast tickBroadcastGpu) ->  {gpu.updateTime();});
        subscribeEvent(TestModelEvent.class,callBack->{

        });
        subscribeBroadcast(TerminateBroadcast.class, (TerminateBroadcast broadcastTerminate) -> this.terminate()); //for closing
    }
}
