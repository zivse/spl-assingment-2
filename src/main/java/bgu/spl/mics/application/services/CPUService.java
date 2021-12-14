package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.TerminateBroadcast;
import bgu.spl.mics.TickBroadcast;
import bgu.spl.mics.application.objects.CPU;

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
        Callback<TickBroadcast> callback = (TickBroadcast broadcast) -> cpu.notify();
        subscribeBroadcast(TickBroadcast.class,callback );
        subscribeBroadcast(TerminateBroadcast.class, (TerminateBroadcast broadcastTerminate) -> this.terminate());
    }

}
