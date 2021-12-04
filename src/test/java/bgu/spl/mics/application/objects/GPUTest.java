package bgu.spl.mics.application.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GPUTest {

    @Before
    public void setUp() throws Exception {
        GPU gpu=new GPU(GPU.Type.GTX1080);
    }

    @Test
    public void train1() {
            GPU gpu=new GPU(GPU.Type.GTX1080);
            assertTrue(gpu.train());
        }


    @Test
    public void test1() {
        GPU gpu=new GPU(GPU.Type.GTX1080);
        assertTrue(gpu.test());
    }
}