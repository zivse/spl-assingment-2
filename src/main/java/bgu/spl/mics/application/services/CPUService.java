package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.TerminateBroadcast;
import bgu.spl.mics.TickBroadcast;
import bgu.spl.mics.application.objects.CPU;
import bgu.spl.mics.application.objects.Data;
import bgu.spl.mics.application.objects.DataBatch;

import java.util.concurrent.CountDownLatch;

/**
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class CPUService extends MicroService {
    private CPU cpu;
    public CPUService(String name, CPU _cpu, CountDownLatch countDown) {
        super(name,countDown);
        cpu = _cpu;
    }
    @Override
    protected void initialize() {
        subscribeBroadcast(TickBroadcast.class,(TickBroadcast broadcast) -> {
            cpu.processData();
        }) ;
        subscribeBroadcast(TerminateBroadcast.class, (TerminateBroadcast c)-> {
                terminate();

        });

}}
