package bgu.spl.mics.application.objects;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUTest {
    private CPU cpu;

    @Before
    public void setUp() throws Exception {
        CPU cpu=new CPU(3);
    }

    @Test
    void processDataT() {
        assertTrue(cpu.ProcessData());
    }
}