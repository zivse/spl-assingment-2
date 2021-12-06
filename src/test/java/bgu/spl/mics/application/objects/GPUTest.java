package bgu.spl.mics.application.objects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GPUTest {
    private GPU gpu;

    @Before
    public void setUp() throws Exception {
       gpu=new GPU(GPU.Type.GTX1080);
    }

    @Test
    public void divideData(){
        DataBatch a= gpu.divideData();
        assertTrue(gpu.isSmaller1000(a));
    }
    @Test
    public void getDataFromCluster(){
        Data data=new Data();
        gpu.getDataFromCluster(data);
        Data test=gpu.getData();
        assertEquals(data,test);
    }
    @Test
    public void trainDataT(){
        assertTrue(gpu.trainData());

    }
}