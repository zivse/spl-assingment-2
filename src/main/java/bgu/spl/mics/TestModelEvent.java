package bgu.spl.mics;

public class TestModelEvent implements Event {
    private Future future;
    public TestModelEvent(){

    }
    public Future getFuture(){
        return future;
    }

    public void setFuture(Future future) {
        this.future = future;
    }
}
