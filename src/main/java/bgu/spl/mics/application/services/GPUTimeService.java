package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.TickBroadcast;
import bgu.spl.mics.application.objects.GPU;

public class GPUTimeService extends MicroService {
    GPU gpu;
    /**
     * @param name the micro-service name (used mainly for debugging purposes -
     *             does not have to be unique)
     */
    public GPUTimeService(String name,GPU _gpu) {
        super(name);
        gpu=_gpu;
    }

    @Override
    protected void initialize() {
        subscribeBroadcast(TickBroadcast.class, new Callback<TickBroadcast>() {
            @Override
            public void call(TickBroadcast c) {
            gpu.updateTime();
            if(gpu.getTime()==gpu.getBeginningTime()+ gpu.getTimeToTrainEachData()){

            }

            }
        });
    }
}
