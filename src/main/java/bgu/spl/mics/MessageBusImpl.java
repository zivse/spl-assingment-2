package bgu.spl.mics;

import bgu.spl.mics.application.objects.Cluster;

import java.util.HashMap;
import java.util.Vector;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	private HashMap<Class<? extends Message>, Vector<MicroService>> microServices;
	private HashMap <Message, Future>  futures;
	private HashMap<MicroService , Vector<Message>> messagesMap;
	private int RRPTrainModelEventCounter;//round Robin Pattern Train Model Event Counter
	private int RRPTestModelEventCounter;//round Robin Pattern Test Model Event Counter
	private int RRPPublishResultsEventCounter;//round Robin Pattern Publish Results Event Counter
	private static MessageBusImpl instance = null;

public MessageBusImpl(){
	microServices=new HashMap<Class<? extends Message>,Vector<MicroService>>();
	microServices.put(TrainModelEvent.class,new Vector<MicroService>());
	microServices.put(TestModelEvent.class,new Vector<MicroService>());
	microServices.put(PublishResultsEvent.class,new Vector<MicroService>());
	microServices.put(PublishConferenceBroadcast.class,new Vector<MicroService>());
	microServices.put(TickBroadcast.class,new Vector<MicroService>());
	futures=new HashMap<Message, Future>();
	messagesMap =new HashMap<MicroService , Vector<Message>>();
	RRPTrainModelEventCounter=0;
	RRPTestModelEventCounter=0;
	RRPPublishResultsEventCounter=0;
}
public static MessageBusImpl getInstance() {
		if(instance == null){
			instance = new MessageBusImpl();
		}
		return instance;
	}
//this methode need to be thread safe!!!!!
	private int addCountRoundRobinPattern(String EventName,int numOfServices){ //get event name and size of the services vector and update the counter of the related services pattern and return the counter before update
	int tempCounter=0;
	if(EventName.compareTo("TrainModelEvent")==0){
		tempCounter=RRPTrainModelEventCounter;
			RRPTrainModelEventCounter=(RRPTrainModelEventCounter+1)%numOfServices;
	}
	else if(EventName.compareTo("TestModelEvent")==0){
		tempCounter=RRPTestModelEventCounter;
		RRPTestModelEventCounter=(RRPTestModelEventCounter+1)%numOfServices;
	}
	else {
			tempCounter=RRPPublishResultsEventCounter;
		RRPPublishResultsEventCounter=(RRPPublishResultsEventCounter+1)%numOfServices;
	}
	return tempCounter;
	}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {

	microServices.get(type).add(m);
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		microServices.get(type).add(m);
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
	  e.getFuture().resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b){
		Class bClass=b.getClass();
		Vector<MicroService>relatedServices=microServices.get(bClass);
		for(MicroService current:relatedServices){
			if(messagesMap.get(current).size()==0){
				messagesMap.get(current).add(b);
				notifyAll();
			}
			else{
				messagesMap.get(current).add(b);
			}
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		Class eClass=e.getClass();
		Vector<MicroService>relatedServices=microServices.get(eClass);
		if(relatedServices==null){
			return null;
		}
		else{
			int indexOfChosenEvent=addCountRoundRobinPattern(eClass.getName(),relatedServices.size());
			MicroService tempServiceToUpdate=relatedServices.get(indexOfChosenEvent);
			if(messagesMap.get(tempServiceToUpdate).size()==0){
				messagesMap.get(tempServiceToUpdate).add(e);
				notifyAll();
			}
			else{
				messagesMap.get(tempServiceToUpdate).add(e);
			}
			Future<T>future=new Future<T>();
			return future;
		}
	}

	@Override
	public void register(MicroService m) {
		messagesMap.put(m,new Vector<Message>());
	}

	@Override
	public void unregister(MicroService m) {
		messagesMap.remove(m);
		for(Vector<MicroService> current:microServices.values()){
			current.remove(m);
		}
	}
	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		Vector<Message> tempMessagesVector = messagesMap.get(m);
		// TODO check for null  nir

		while (true) {
			Message message;
			try {
				message = tempMessagesVector.remove(0);
			} catch (ArrayIndexOutOfBoundsException ignore) {
				message = null;
			}

			if (message != null) {
				return message;
			}
			Object lock=new Object();
			synchronized(lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
				}
			}

		}
	}


/*
	//for test
	public<T>boolean checkIsReallyAddedSub(Class<? extends Event<T>> eventType, MicroService micro){
		return messagesMap.get(eventType).contains(micro);
	}
	public<T>boolean checkisReallyAddedSubBroad(Class<? extends Broadcast> eventType, MicroService micro){
			return microServices.get(eventType).contains(micro);
	}
	public boolean hasBro(Broadcast bro, MicroService micro){
			return microServices.get(micro).contains(bro);
	}
	public<T> boolean conEvent(Event<T> event, MicroService micro){
		return microServices.get(micro).contains(event);
	}
	public boolean registerIsTrue(MicroService micro){
			return messagesMap.containsKey(micro);

	}
*/
}
