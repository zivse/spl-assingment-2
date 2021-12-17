package bgu.spl.mics;

import bgu.spl.mics.application.objects.Cluster;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	private ConcurrentHashMap<Class<? extends Message>, Vector<MicroService>> microServices;
	private ConcurrentHashMap <Message, Future>  futures;
	private ConcurrentHashMap<MicroService , BlockingDeque<Message>> messagesMap;
	private AtomicInteger RRPTrainModelEventCounter;//round Robin Pattern Train Model Event Counter
	private AtomicInteger RRPTestModelEventCounter;//round Robin Pattern Test Model Event Counter
	private AtomicInteger RRPPublishResultsEventCounter;//round Robin Pattern Publish Results Event Counter
	private static MessageBusImpl instance = null;
	private static Object lock=new Object();

public MessageBusImpl(){
	microServices=new ConcurrentHashMap<Class<? extends Message>,Vector<MicroService>>();
	microServices.put(TrainModelEvent.class,new Vector<MicroService>());
	microServices.put(TestModelEvent.class,new Vector<MicroService>());
	microServices.put(PublishResultsEvent.class,new Vector<MicroService>());
	microServices.put(PublishConferenceBroadcast.class,new Vector<MicroService>());
	microServices.put(TickBroadcast.class,new Vector<MicroService>());
	microServices.put(TerminateBroadcast.class,new Vector<MicroService>());
	futures=new ConcurrentHashMap<Message, Future>();
	messagesMap =new ConcurrentHashMap<MicroService , BlockingDeque<Message>>();
	RRPTrainModelEventCounter=new AtomicInteger();
	RRPTestModelEventCounter=new AtomicInteger();
	RRPPublishResultsEventCounter=new AtomicInteger();
}
public static MessageBusImpl getInstance() {
		if(instance == null){
			instance = new MessageBusImpl();
		}
		return instance;
	}
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		synchronized(lock) {
			microServices.get(type).add(m);
		}
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		synchronized(lock) {
			microServices.get(type).add(m);
		}
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		System.out.println("complete in message bus"+e);
	  e.getFuture().resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b){
		Class bClass=b.getClass();
		Vector<MicroService>relatedServices=microServices.get(bClass);
		for(MicroService current:relatedServices){
				messagesMap.get(current).add(b);
				}
	}


	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		synchronized(lock) {
		Class eClass=e.getClass();
		Vector<MicroService>relatedServices=microServices.get(eClass);
		if(relatedServices==null){
			return null;
		}
		else{
				String EventName = eClass.getName();
				int numOfServices=microServices.get(eClass).size();
				if (EventName.compareTo("bgu.spl.mics.TrainModelEvent") == 0) {
					MicroService tempMicroService=microServices.get(e.getClass()).get(RRPTrainModelEventCounter.getAndIncrement()%numOfServices);
					messagesMap.get(tempMicroService).add(e);
				} else if (EventName.compareTo("bgu.spl.mics.TestModelEvent") == 0) {
					MicroService tempMicroService=microServices.get(e.getClass()).get(RRPTestModelEventCounter.getAndIncrement()%numOfServices);
					messagesMap.get(RRPTestModelEventCounter.getAndIncrement()%numOfServices).add(e);
				} else {
					MicroService tempMicroService=microServices.get(e.getClass()).get(RRPPublishResultsEventCounter.getAndIncrement()%numOfServices);
					messagesMap.get(RRPPublishResultsEventCounter.getAndIncrement()%numOfServices).add(e);
				}
				Future<T> future = new Future<T>();
				return future;
			}
		}
	}

	@Override
	public void register(MicroService m) {
		messagesMap.put(m, new LinkedBlockingDeque<Message>());
	}

	@Override
	public void unregister(MicroService m) {
		synchronized(lock) {
			BlockingDeque<Message>tempMessageDeque=messagesMap.remove(m);
			for (Message currentM :tempMessageDeque) {
				try {
					messagesMap.get(currentM.getClass()).remove(m);
				}catch(NullPointerException e){}
			}
		}
	}
	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		BlockingDeque<Message> tempMessagesBlockingDeque = messagesMap.get(m);
		try {
			return tempMessagesBlockingDeque.take();
		}catch(InterruptedException e){throw e;}
	}
}
