package bgu.spl.mics;

public class PublishResultsEvent implements Event {
    private Future future;
    public PublishResultsEvent(){

    }
    public Future getFuture(){
        return future;
    }
}
