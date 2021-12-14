package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.PublishConferenceBroadcast;
import bgu.spl.mics.PublishResultsEvent;
import bgu.spl.mics.TickBroadcast;
import bgu.spl.mics.application.objects.ConfrenceInformation;
import bgu.spl.mics.application.objects.Model;

/**
 * Conference service is in charge of
 * aggregating good results and publishing them via the {@link PublishConfrenceBroadcast},
 * after publishing results the conference will unregister from the system.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class ConferenceService extends MicroService {
    private ConfrenceInformation conference;
    public ConferenceService(String _name,ConfrenceInformation _conference) {
        super(_name);
        conference=_conference;
    }
    @Override
    protected void initialize() {
        subscribeBroadcast(TickBroadcast.class, (event) -> {
            conference.updateTime();
            if(conference.getTime()==conference.getDate()){
                sendBroadcast(new PublishConferenceBroadcast(conference.getConnectStudentToArticles(),conference.getTotalPublishers()));
                terminate();
            }
        });
        subscribeEvent(PublishResultsEvent.class,(event)->{
            Model model=event.getModel();
            conference.setConnectStudentToArticles(model.getStudent(),model);
        });

    }
}
