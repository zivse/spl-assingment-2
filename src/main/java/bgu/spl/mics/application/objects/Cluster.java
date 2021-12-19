package bgu.spl.mics.application.objects;


import java.util.Comparator;
import java.util.Vector;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Passive object representing the cluster.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Cluster {
	Object cLock=new Object();
	Object gLock=new Object();
	private static Cluster instance = new Cluster();
	private Vector<GPU> gpusVector;
	private PriorityBlockingQueue<CPU> cpuPriority;
	private Cluster(){
		gpusVector=new Vector<GPU>();
	}
	public void addCPU(Vector<CPU> cpuVector){
		cpuPriority = new PriorityBlockingQueue(cpuVector.size(),new cpuComp());
		cpuPriority.addAll(cpuVector);
	}
	private static class cpuComp implements Comparator<CPU>{

		public int compare(CPU cpu1, CPU cpu2) {
			if (cpu1.getTimeToCompleteData() > cpu2.getTimeToCompleteData()) {
				return 1;
			} else if (cpu1.getTimeToCompleteData() < cpu2.getTimeToCompleteData()) {
				return -1;
			} else {
				if (cpu1.getCores() > cpu2.getCores()) {
					return -1;
				} else if (cpu1.getCores() < cpu2.getCores()) {
					return 1;
				} else {
					return 0;
				}
			}
		}

	}
	public void addGPU(GPU gpu){
		gpusVector.add(gpu);
	}

	public void processData(DataBatch dataToProcess) { //need to pick available cpu and tell him to process
		synchronized (cLock) {
			CPU processCpu = cpuPriority.poll(); //need to choose smart
			cpuPriority.add(processCpu);
			processCpu.addDataBatch(dataToProcess);
		}
	}

	public void sendDataToGpu(DataBatch dataBatch){
		synchronized (gLock) {
			dataBatch.getGPU().addDataBatchToTrain(dataBatch);
		}
	}

		/**
		 * Retrieves the single instance of this class.
		 */
		public static Cluster getInstance(){
			return instance;
		}

}
