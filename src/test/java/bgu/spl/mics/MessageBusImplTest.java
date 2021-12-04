package bgu.spl.mics;

import bgu.spl.mics.application.services.CPUService;
import bgu.spl.mics.example.messages.ExampleEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageBusImplTest {

    private MessageBus bus;
    private MicroService m;
    private ExampleEvent e;

    @Before
    public void setUp() throws Exception {
        bus = new  MessageBusImpl();
        m = new CPUService("c");
        e = new ExampleEvent("e");
    }

    @Test
    public void subscribeEvent() {
        assertNull(m);
        int size= bus.getEvents().size();
        bus.subscribeEvent(e.getClass(),m) ;
        assertEquals(size+1,bus.getEvents().size());
    }

    @Test
    public void subscribeBroadcast() {
        assertNull(m);
        int size= bus.getBroadcast().size();
        subscribeEvent(ExampleEvent,m) ;
        assertEquals(size+1,bus.sizeEvents());
    }

    @Test
    public void complete() {

        complete(x,"result");
        assertEquals("result",x.getResult());


    }

    @Test
    public void sendBroadcast() {
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