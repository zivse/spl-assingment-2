package bgu.spl.mics.application.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUTest {


    @Test
    void processDataT() {
        CPU cpu1=new CPU(3);
        assertTrue(cpu1.ProcessData());
    }
}