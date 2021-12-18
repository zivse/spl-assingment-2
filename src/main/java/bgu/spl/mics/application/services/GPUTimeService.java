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
                if(gpu.getModel()!=null){
                    //initialization
                int timeToTrainEachData = gpu.getTimeToTrainEachData();
                boolean isActive = gpu.getIsActiveTrain();
                int beginningTime = gpu.getBeginningTime();
                int time = gpu.getTime();
                DataBatch data = gpu.getCurrentDataToTrain();
                //actions
                if (isActive && time - beginningTime == timeToTrainEachData) {
                    gpu.updateAlreadyTrainedData();
                    gpu.updateCurrentDataToTrain();
                    if (gpu.getCurrentDataToTrain() != null) {
                        gpu.setBeginningTime();
                    }
                }
                    for(int i=0;i<6&&gpu.getDataToTrainVectorSize()<gpu.getMemory()&&!gpu.splitedDataLinkedListIsEmpty();i++){
                        gpu.getCluster().processData(gpu.takeDataToTrainFromsplitedDataLinkedList()) ;
                    }
                    if(gpu.getIsFinishedTrained()){
                        gpu.getModel().setStatus(Model.Status.Trained);
                    complete(gpu.getTrainModelEvent(),"trained");
                    }
        }});
    }

}