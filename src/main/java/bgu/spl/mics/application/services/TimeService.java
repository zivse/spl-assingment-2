package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.PublishResultsEvent;
import bgu.spl.mics.TickBroadcast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * TimeService is the global system timer There is only one instance of this micro-service.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other micro-services about the current time tick using {@link TickBroadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends MicroService{
	private int tickTime;
	private int duration;
	private int currentTime;

	public TimeService(int _tickTime, int _duration) {
		super("TimeService");
		tickTime=_tickTime;
		duration=_duration;
		currentTime=1;
	}

	@Override
	protected void initialize() {
		Timer timer = new Timer();
		while (currentTime < duration){
			TimerTask task=new TimerTask() {
				@Override
				public void run() {
					sendBroadcast(new TickBroadcast());
				}
			};
			timer.schedule(task,tickTime);
			currentTime += 1;
		}
	}

}
