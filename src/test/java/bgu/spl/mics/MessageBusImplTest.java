package bgu.spl.mics;

import bgu.spl.mics.application.services.CPUService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageBusImplTest {

    @Before
    public void setUp() throws Exception {
        MessageBus bus = new  MessageBusImpl();
        MicroService m= new CPUService("c");
    }

    @Test
    public void subscribeEvent() {
        MessageBus bus = new  MessageBusImpl();
        MicroService m= new CPUService("c");
        assertNull(m);
        int size= bus.getEvents().size();
        subscribeEvent(ExampleEvent,m) ;
        assertEquals(size+1,bus.getEvents().size());
    }

    @Test
    public void subscribeBroadcast() {
        MessageBus bus = new  MessageBusImpl();
        MicroService m= new CPUService("c");
        assertNull(m);
        int size= bus.getBroadcast().size();
        subscribeEvent(ExampleEvent,m) ;
        assertEquals(size+1,bus.sizeEvents());
    }

    @Test
    public void complete() {
        MessageBus bus = new  MessageBusImpl();
        MicroService m= new CPUService("c");
        Event x= new <String>ExampleEvent();
        complete(x,"result");
        assertEquals("result",x.getResult());


    }

    @Test
    public void sendBroadcast() {
        MessageBus bus = new  MessageBusImpl();
        MicroService m= new CPUService("c");
        Broadcast x= new Broadcast() ;
        assertNull(x);
        int size= bus.getBroadcast().size();
        sendBroadcast(x);
        assertEquals(size+1,getBroadcast().size());

    }

    @Test
    public void sendEvent() {
        MessageBus bus = new  MessageBusImpl();
        Event e= new ExampleEvent();
        assertNull(e);
        int size= bus.getEvents().size();
        sendEvent(e) ;
        assertEquals(size+1,bus.getEvents().size());
    }

    @Test
    public void register() {
    }

    @Test
    public void unregister() {
    }

    @Test
    public void awaitMessage() {
    }
}