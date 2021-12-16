package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.TerminateBroadcast;
import bgu.spl.mics.TickBroadcast;
import bgu.spl.mics.application.objects.CPU;
import bgu.spl.mics.application.objects.Data;
import bgu.spl.mics.application.objects.DataBatch;

import java.util.LinkedList;

/**
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class CPUService extends MicroService {
    private CPU cpu;
    public CPUService(String name, CPU _cpu) {
        super(name);
        cpu = _cpu;
    }
    @Override
    protected void initialize() {
        Callback<TickBroadcast> callback = (TickBroadcast broadcast) ->{
            cpu.updateTime();
            boolean isActive=cpu.getIsActive();
            int beginningTime=cpu.getBeginningTime();
            int time=cpu.getTime();
            DataBatch data=cpu.getCurrentDataBatch();
            Data.Type type = data.getDataFromBath().getType();
            int cores=cpu.getCores();
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
        subscribeBroadcast(TickBroadcast.class,callback );
        subscribeBroadcast(TerminateBroadcast.class, new Callback<TerminateBroadcast>() {
            @Override
            public void call(TerminateBroadcast c) {
                terminate();
            }
        });

}}
