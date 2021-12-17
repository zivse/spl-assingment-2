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
	Object lock=new Object();
	private static Cluster instance = null;
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
			if (cpu1.getTotalProcessDataAmount()-cpu1.getAlreadyProcessedDataTime() > cpu2.getTotalProcessDataAmount()-cpu2.getAlreadyProcessedDataTime()) {
				return 1;
			} else if (cpu1.getTotalProcessDataAmount()-cpu1.getAlreadyProcessedDataTime() < cpu2.getTotalProcessDataAmount()-cpu2.getAlreadyProcessedDataTime()) {
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
public void processData(DataBatch dataToProcess){ //need to pick available cpu and tell him to process
		synchronized(lock) {
			CPU processCpu = cpuPriority.poll(); //need to choose smart TODO:implement this
			cpuPriority.add(processCpu);
			DataBatch cpuCurrentDataBatch = processCpu.getCurrentDataBatch();
			processCpu.setTimeToProcessCurrentData(dataToProcess);
			if (cpuCurrentDataBatch == null) {
				processCpu.setCurrentDataBatch(dataToProcess);
				processCpu.setBeginningTime();
			}
			else {
				processCpu.addDataBatch(dataToProcess);
			}
			processCpu.incrementTotalProcessDataAmount();
			processCpu.setActive();
		}
}
public void trainData(DataBatch dataToTrain) {
	synchronized (lock) {
		GPU gpuToTrain = dataToTrain.getGPU();
		DataBatch gpuCurrentDataBatch = gpuToTrain.getCurrentDataToTrain();
		if (gpuCurrentDataBatch == null) {
			gpuToTrain.setCurrentDataToTrain(dataToTrain);
			gpuToTrain.setBeginningTime();
		} else {
			gpuToTrain.addDataToTrainToVector(dataToTrain);
		}
		gpuToTrain.setActiveTrain(true);
		if(gpuToTrain.getIndexCurrentData()<gpuToTrain.getModelDataSize()){
			gpuToTrain.splitData();
		}
		}
	}


	/**
     * Retrieves the single instance of this class.
     */
	public static Cluster getInstance() {
		if(instance == null){
			instance = new Cluster();
		}
		return instance;
	}

}
