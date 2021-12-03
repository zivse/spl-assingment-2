package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;

import java.util.concurrent.TimeUnit;


class FutureTest {
    private static Future<String> future;

    @BeforeEach
    void setUp() {
        future=new Future<>();
    }

    @Test
    void getT() {
        String check="check result";
        future.resolve(check);
        assertEquals(check,future.get());
    }

    @Test
    void resolveT() {
        String check="check result";
        future.resolve(check);
        assertEquals(check,future.get());
    }

    @Test
    void isDoneT() {
        assertFalse(future.isDone());
        future.resolve("check");
        assertTrue(future.isDone());
    }

    @Test
    void testGet() {
        assertNull(future.getfResult());
        assertThrows("time is negative",Exception.class,()->future.get(-1, TimeUnit.SECONDS));
        long t1=20;
        TimeUnit t2=TimeUnit.SECONDS;
        String checkResult="check";
        Thread test1= new Thread( ()-> assertEquals(checkResult, future.get(t1, t2)));
        Thread test2= new Thread( ()-> future.resolve(checkResult));
        test1.start();
        //test 1 is going to sleep
        test2.start();
    }
}