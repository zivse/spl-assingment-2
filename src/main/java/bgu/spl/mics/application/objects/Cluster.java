package bgu.spl.mics.application.objects;


import java.util.Vector;

/**
 * Passive object representing the cluster.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Cluster {
	private static Cluster instance = null;
	Vector<GPU>gpusVector;
	Vector<CPU>cpusVector;
	public Cluster(){
		gpusVector=new Vector<GPU>();
		cpusVector=new Vector<CPU>();
	}
	public void addCPU(CPU cpu){

		cpusVector.add(cpu);
	}
	public void addGPU(GPU gpu){

		gpusVector.add(gpu);
	}
public void processData(DataBatch dataToProcess){ //need to pick available cpu and tell him to process
		CPU c=new CPU(16); //need to choose smart TODO:implement this
		DataBatch cpuCurrentDataBatch=c.getCurrentDataBatch();
		if(cpuCurrentDataBatch==null){
			c.setCurrentDataBatch(dataToProcess);
			c.setBeginningTime(c.getTime());
		}
		c.addDataBatch(dataToProcess);
		c.setActive();
}
public void trainData(DataBatch dataToTrain){

	dataToTrain.getGPU().addData(dataToTrain);
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
