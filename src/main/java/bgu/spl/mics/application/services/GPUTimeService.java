package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.TerminateBroadcast;
import bgu.spl.mics.TickBroadcast;
import bgu.spl.mics.application.objects.Data;
import bgu.spl.mics.application.objects.DataBatch;
import bgu.spl.mics.application.objects.GPU;
import bgu.spl.mics.application.objects.Model;

import java.util.concurrent.CountDownLatch;

public class GPUTimeService extends MicroService {
    GPU gpu;

    /**
     * @param name the micro-service name (used mainly for debugging purposes -
     *             does not have to be unique)
     */
    public GPUTimeService(String name, GPU _gpu, CountDownLatch countDown) {
        super(name,countDown);
        gpu = _gpu;
    }

    @Override
    protected void initialize() {
        subscribeBroadcast(TerminateBroadcast.class,(TerminateBroadcast c)-> {
                terminate();
        });
        subscribeBroadcast(TickBroadcast.class, (TickBroadcast c)-> {
                gpu.updateTime();
                //System.out.println("gpu time service received a tick");
                int timeToTrainEachData = gpu.getTimeToTrainEachData();
                boolean isActive = gpu.getIsActiveTrain();
                int beginningTime = gpu.getBeginningTime();
                int time = gpu.getTime();
                DataBatch data = gpu.getCurrentDataToTrain();
                if (isActive && time - beginningTime == timeToTrainEachData) {
                    gpu.incrementMemoryBy1();
                    if(gpu.getMemory()>0){
                        gpu.splitData();
                    }
                    gpu.updateCurrentDataToTrain();
                    if (gpu.getCurrentDataToTrain() != null) {
                        gpu.setBeginningTime(time);
                        gpu.updateAlreadyTrainedData();
                    }
                }
                if(gpu.getModel()!=null){
                    if(gpu.getIsFinishedTrained()){
                        gpu.getModel().setStatus(Model.Status.Trained);
                    complete(gpu.getTrainModelEvent(),"trained");
                    }
                }
        });
    }

}