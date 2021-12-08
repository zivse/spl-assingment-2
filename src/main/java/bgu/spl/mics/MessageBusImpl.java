package bgu.spl.mics;

import java.util.HashMap;
import java.util.Vector;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	private HashMap<Class<? extends Message>, Vector<MicroService>> microServices;
	private HashMap <Message, Future>  futers;
	private HashMap<MicroService , Vector<Message>> messages;

		@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(MicroService m) {
		//microServices.put(,m);

	}

	@Override
	public void unregister(MicroService m) {
		// TODO Auto-generated method stub

	}
	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}
	public<T>boolean checkIsReallyAddedSub(Class<? extends Event<T>> eventType, MicroService micro){
		return messages.get(eventType).contains(micro);
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
			return messages.containsKey(micro);

	}

}
