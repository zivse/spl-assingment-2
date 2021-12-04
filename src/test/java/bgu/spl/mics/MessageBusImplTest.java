package bgu.spl.mics;

import bgu.spl.mics.application.services.CPUService;
import bgu.spl.mics.example.messages.ExampleBroadcast;
import bgu.spl.mics.example.messages.ExampleEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageBusImplTest {

    private MessageBus bus;
    private MicroService m;
    private ExampleEvent e;
    private ExampleBroadcast bro;
    private Future<Integer> future;

    @Before
    public void setUp() throws Exception {
        bus = new  MessageBusImpl();
        m = new CPUService("c");
        e = new ExampleEvent("e");
        bus.register(m); //register the microservice before all the tests.
    }

    @Test
    public void subscribeEvent() {
        bus.subscribeEvent(e.getClass(),m);
        assertTrue(bus.checkSub(e.getClass(),m));
    }

    @Test
    public void subscribeBroadcast() {
        bus.subscribeBroadcast(bro.getClass(), m);
        assertTrue(bus.checkSubBroad(bro.getClass(),m));
    }

    @Test
    public void complete() {
       future.resolve(10);//give future some default value
       assertTrue(future.isDone());
    }

    @Test
    public void sendBroadcast() {
        bus.subscribeBroadcast(bro.getClass(),m);
        bus.sendBroadcast(bro);
        assertTrue(bus.hasBro(bro,m));
    }

    @Test
    public void sendEvent() {
        bus.subscribeEvent(e.getClass(),m);
        bus.sendEvent(e);
        assertTrue(bus.conEvent(e,m)); //check if the event has sent
    }

    @Test
    public void register() { //we register m on the before
        assertTrue(bus.registerIsTrue(m));
    }

    @Test
    public void unregister() {
        bus.unregister(m);
        assertFalse(bus.registerIsTrue(m));
    }

    @Test
    public void awaitMessage() {

    }
}