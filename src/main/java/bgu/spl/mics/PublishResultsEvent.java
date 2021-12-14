package bgu.spl.mics;

import bgu.spl.mics.application.objects.Model;

public class PublishResultsEvent implements Event {
    private Future future;
    private Model model;
    public PublishResultsEvent(Model _model){
    model=_model;
    }
    public Future getFuture(){
        return future;
    }
    public Model getModel(){
        return model;
    }
}
