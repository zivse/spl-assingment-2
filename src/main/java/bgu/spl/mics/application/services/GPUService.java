package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.objects.GPU;
import bgu.spl.mics.application.objects.Model;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

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
                    gpu.testModel(testModelEvent.getModel());
                });
        subscribeEvent(TrainModelEvent.class, (TrainModelEvent c)-> {

            gpu.train(c);
                });
        subscribeBroadcast(TerminateBroadcast.class,(TerminateBroadcast c)-> {
                terminate();
        });
        subscribeBroadcast(TickBroadcast.class, (TickBroadcast c)->{
            gpu.trainDataBatch();
        });
    }
}
