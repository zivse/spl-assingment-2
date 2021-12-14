package bgu.spl.mics;

import java.util.concurrent.TimeUnit;

/**
 * A Future object represents a promised result - an object that will
 * eventually be resolved to hold a result of some operation. The class allows
 * Retrieving the result once it is available.
 * 
 * Only private methods may be added to this class.
 * No public constructor is allowed except for the empty constructor.
 */
public class Future<T> {
	private T fResult;
	
	/**
	 * This should be the only public constructor in this class.
	 */
	public Future() {
		fResult=null;
	}
	
	/**
     * retrieves the result the Future object holds if it has been resolved.
     * This is a blocking method! It waits for the computation in case it has
     * not been completed.
     * <p>
     * @return return the result of type T if it is available, if not wait until it is available.
     * @pre none
	 * @post none
     */
	public T get()  { //check the meaning
		while (fResult == null) try {
			wait();
		} catch (InterruptedException ignore) {
		}
		return fResult;
	}

	public T getfResult() {
		return fResult;
	}

	/**
     * Resolves the result of this Future object.
	 * @param result the result that need to be passed
	 * @pre none
	 * @post this.get() == @param result
     */
	public void resolve (T result) {
		fResult = result;
		notifyAll();
	}
	
	/**
     * @return true if this object has been resolved, false otherwise
	 * @pre none
	 * @post none
     */
	public boolean isDone() {
		if(fResult != null){
			return true;
		}
		return false;
	}
	
	/**
     * retrieves the result the Future object holds if it has been resolved,
     * This method is non-blocking, it has a limited amount of time determined
     * by {@code timeout}
     * <p>
     * @param timeout 	the maximal amount of time units to wait for the result.
     * @param unit		the {@link TimeUnit} time units to wait.
     * @return return the result of type T if it is available, if not, 
     * 	       wait for {@code timeout} TimeUnits {@code unit}. If time has
     *         elapsed, return null.
	 * @pre timeout>=0
	 * @post none
     */
	public T get(long timeout, TimeUnit unit) throws InterruptedException { //check!
		long timeoutInt = TimeUnit.MILLISECONDS.convert(timeout, unit);//convert to milliseconds
		if(fResult != null){
			getfResult();
		}

		else if(fResult == null){
			wait(timeoutInt);
		}
		return null;
	}

}
