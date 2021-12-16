package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.TickBroadcast;
import bgu.spl.mics.application.objects.Data;
import bgu.spl.mics.application.objects.DataBatch;
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
            boolean isActive= gpu.getIsActiveTrain();
            int beginningTime=gpu.getBeginningTime();
            int time=gpu.getTime();
            DataBatch data=gpu.getCurrentDataToTrain();
            if(isActive)


            if(gpu.getTime()==gpu.getBeginningTime()+ gpu.getTimeToTrainEachData()&&gpu.getIsActiveTrain()){
            gpu.updateAlreadyTrainedData();
            }
            }
        });




            switch (type) {
                case Tabular: {
                    if(isActive&&time-beginningTime==(32/cores)) {
                        cpu.getCluster().trainData(data);
                        cpu.updateCurrentDataToProcess();
                        if(cpu.getCurrentDataBatch()!=null)  {
                            cpu.setBeginningTime(time);
                        }
                    }
                }
                case Text: {
                    if(isActive&&time-beginningTime==(32/cores)*2) {
                        cpu.getCluster().trainData(data);
                        cpu.updateCurrentDataToProcess();
                        if(cpu.getCurrentDataBatch()!=null)  {
                            cpu.setBeginningTime(time);
                        }
                    }
                }
                case Images: {
                    if(isActive&&time-beginningTime==(32/cores)*4) {
                        cpu.getCluster().trainData(data);
                        cpu.updateCurrentDataToProcess();
                        if(cpu.getCurrentDataBatch()!=null)  {
                            cpu.setBeginningTime(time);
                        }
                    }
                }
            }
        } ;
    }
}
