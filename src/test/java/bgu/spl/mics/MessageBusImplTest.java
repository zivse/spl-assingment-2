package bgu.spl.mics;

import bgu.spl.mics.application.services.CPUService;
import bgu.spl.mics.example.messages.ExampleBroadcast;
import bgu.spl.mics.example.messages.ExampleEvent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageBusImplTest {
/*
    private MessageBus bus;
    private MicroService ms1;
    private ExampleEvent ev;
    private ExampleBroadcast bro;
    private Future<Integer> future;

    @Before
    public void setUp() throws Exception {
        ms1 = new CPUService("c");
        ev = new ExampleEvent("e");
        bus = new  MessageBusImpl();
        bus.register(ms1); //register the microservice before all the tests.
    }

    @Test
    public void subscribeEvent() {
        bus.subscribeEvent(ev.getClass(),ms1);
        assertTrue(bus.checkIsReallyAddedSub(ev.getClass(),ms1));
    }

    @Test
    public void subscribeBroadcast() {
        bus.subscribeBroadcast(bro.getClass(), ms1);
        assertTrue(bus.checkisReallyAddedSubBroad(bro.getClass(),ms1));
    }
    @Test
    public void sendBroadcast() {
        bus.subscribeBroadcast(bro.getClass(),ms1);
        bus.sendBroadcast(bro);
        assertTrue(bus.hasBro(bro,ms1));
    }
    @Test
    public void complete() {
       future.resolve(10);//give future some default value
       assertTrue(future.isDone());
    }



    @Test
    public void sendEvent() {
        bus.subscribeEvent(ev.getClass(),ms1);
        bus.sendEvent(ev);
        assertTrue(bus.conEvent(ev,ms1)); //check if the event has sent
    }

    @Test
    public void register() { //we register m on the before

        assertTrue(bus.registerIsTrue(ms1));
    }

    @Test
    public void unregister() {
        bus.unregister(ms1);
        assertFalse(bus.registerIsTrue(ms1));
    }
*/

}