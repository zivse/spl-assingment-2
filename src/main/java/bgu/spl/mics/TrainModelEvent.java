package bgu.spl.mics;

import bgu.spl.mics.application.objects.Model;

public class TrainModelEvent implements Event{
    private Model modelToTrain;
    public TrainModelEvent(Model _model){
        modelToTrain = _model;
    }

}
