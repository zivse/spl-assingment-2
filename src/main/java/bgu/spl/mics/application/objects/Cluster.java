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
			if (cpu1.getCountOfDataToProcess()-cpu1.getAlreadyTrainedDataTime() > cpu2.getCountOfDataToProcess()-cpu2.getAlreadyTrainedDataTime()) {
				return 1;
			} else if (cpu1.getCountOfDataToProcess()-cpu1.getAlreadyTrainedDataTime() < cpu2.getCountOfDataToProcess()-cpu2.getAlreadyTrainedDataTime()) {
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
		CPU processCpu = cpuPriority.poll(); //need to choose smart TODO:implement this
		cpuPriority.add(processCpu);
		DataBatch cpuCurrentDataBatch=processCpu.getCurrentDataBatch();
		if(cpuCurrentDataBatch==null){
			processCpu.setCurrentDataBatch(dataToProcess);
			processCpu.setBeginningTime(processCpu.getTime());
		}
	    processCpu.setCountOfDataToProcess(dataToProcess);
		processCpu.addDataBatch(dataToProcess);
		processCpu.setActive();
}
public void trainData(DataBatch dataToTrain){
		GPU gpuToTrain=dataToTrain.getGPU();
	    DataBatch gpuCurrentDataBatch=gpuToTrain.getCurrentDataToTrain();
	if(gpuCurrentDataBatch==null){
		gpuToTrain.setGpuCurrentDataBatch(dataToTrain);
		gpuToTrain.setBeginningTime(gpuToTrain.getTime());
	}
	gpuToTrain.addData(dataToTrain);
	gpuToTrain.setActiveTrain(true);
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
