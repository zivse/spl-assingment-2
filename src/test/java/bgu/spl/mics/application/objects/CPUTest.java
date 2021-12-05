package bgu.spl.mics.application.objects;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUTest {
    private CPU cpu;

    @Before
    public void setUp() throws Exception {
        cpu=new CPU(3);
    }

    @Test
    void processDataT() {
        DataBatch check= new DataBatch();
        assertEquals(check,cpu.ProcessData(check));
    }

    @Test
    void updateTime(){
        int time = cpu.getTime();
        cpu.updateTime();
        assertEquals(time+1,cpu.getTime());
    }
    @Test
    public void addedUnProcessedData(){
        DataBatch check= new DataBatch();
        assertNull(check);
        cpu.addUnProcessedData(check);
        assertTrue(cpu.isAddedData(check));
    }

}