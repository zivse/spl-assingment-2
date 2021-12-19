package bgu.spl.mics;

import bgu.spl.mics.application.objects.Model;

import javax.jws.WebParam;

public class TestModelEvent implements Event {
    private Future future;
    private Model model;
    public TestModelEvent(Model model){
        model = model;
    }
    public Future getFuture(){
        return future;
    }

    public void setFuture(Future future) {
        this.future = future;
    }
    public Model getModel(){
        return model;
    }
}
