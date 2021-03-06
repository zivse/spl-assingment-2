package bgu.spl.mics.application;
import bgu.spl.mics.application.objects.*;
import bgu.spl.mics.application.services.*;
import com.google.gson.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/** This is the Main class of Compute Resources Management System application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output a text file.
 */
public class CRMSRunner {
    public static void main(String[] args){
        Path path = Paths.get("example_input-3.json");
        Reader reader = null;
        Vector<Thread> threadVector=new Vector<>();
        try{
            reader = Files.newBufferedReader(path,StandardCharsets.UTF_8);
        }
        catch (IOException ignored) {
        };
        JsonParser parser = new JsonParser();
        JsonElement tree = parser.parse(reader);
        JsonObject object = tree.getAsJsonObject();
        JsonArray students = object.get("Students").getAsJsonArray();

        Cluster cluster=Cluster.getInstance();
        Vector<GPU> gpuVector = new Vector<>();
        JsonArray GPUArray = object.get("GPUS").getAsJsonArray();
        for (JsonElement gpu : GPUArray){
            GPU Gpu = new GPU(gpu.getAsString());
            gpuVector.add(Gpu);
        }

        JsonArray CPUArray = object.get("CPUS").getAsJsonArray();
        Vector<CPU> cpuVector = new Vector<>();
        for (JsonElement cpu: CPUArray){
            CPU Cpu = new CPU(cpu.getAsInt());
            cpuVector.add(Cpu);
        }
        cluster.addCPU(cpuVector);

         int counterThreads = cpuVector.size() + gpuVector.size();
        CountDownLatch counterThreadToRun = new CountDownLatch(counterThreads);
        for(GPU gpu: gpuVector){
            GPUService gpuService = new GPUService("gpu", gpu,counterThreadToRun );
            Thread gpuThread = new Thread(gpuService);
            threadVector.add(gpuThread);
            cluster.addGPU(gpu);
            gpuThread.start();
        }
        for(CPU cpu: cpuVector){
            CPUService cpuService=new CPUService("cpu",cpu, counterThreadToRun);
            Thread cpuThread=new Thread(cpuService);
            threadVector.add(cpuThread);
            cpuThread.start();
        }

        try{
            counterThreadToRun.await();
        }
        catch (InterruptedException ignored){}
//students + models
        Vector<Student> studentsVector = new Vector<>();
        for(JsonElement studentElement : students){
            JsonObject studentObject = studentElement.getAsJsonObject();
            String studentName = studentObject.get("name").getAsString();
            String studentDepartment = studentObject.get("department").getAsString();
            String  studentStatus = studentObject.get("status").getAsString();
            Vector<Model> modelsVector = new Vector<>();
            Student student = new Student(studentName, studentDepartment, studentStatus, modelsVector);
            studentsVector.add(student);
            JsonArray models = studentObject.get("models").getAsJsonArray();
            for(JsonElement model : models){
                JsonObject modelObject = model.getAsJsonObject();
                String modelName = modelObject.get("name").getAsString();
                String modelType = modelObject.get("type").getAsString();
                int modelSize = modelObject.get("size").getAsInt();
                Data tempModelData = new Data(modelType, modelSize);
                Model tempmodel = new Model(modelName, tempModelData,student);
                modelsVector.add(tempmodel);
                student.setModelsVector(modelsVector);
            }

        }
       CountDownLatch studentCountDown = new CountDownLatch(studentsVector.size());
       for(Student student: studentsVector){
            StudentService studentService = new StudentService(student,studentCountDown);
            Thread studentServiceThread = new Thread(studentService);
            threadVector.add(studentServiceThread);
            studentServiceThread.start();

        }
        try{
            studentCountDown.await();
        }catch(InterruptedException ignored){}
        int tickTime = object.get("TickTime").getAsInt();
        int duration = object.get("Duration").getAsInt();
        TimeService timeService = new TimeService(tickTime, duration);
        Thread timeThread=new Thread(timeService);
        timeThread.start();
        threadVector.add(timeThread);
        for( Thread currentThread:threadVector){
            try{
            currentThread.join();}
            catch(InterruptedException ignored){}
        }
        int totalCpu = 0;
        for(CPU cpu : cpuVector){
            totalCpu = totalCpu + cpu.getTotalCpuTime();
        }
        int totalGpu = 0;
        for(GPU gpu: gpuVector){
            totalGpu = totalGpu + gpu.getTotalGPUTime();
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("Output.txt");
            fileWriter.write(studentsVector.toString());
            fileWriter.write("total cpu " + totalCpu);
            fileWriter.write("total gpu " + totalGpu);
            fileWriter.write("total batches " + cluster.getBatches());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        finally {
            if(fileWriter != null){
                try{
                    fileWriter.close();
                }
                catch(IOException exception){
                    exception.printStackTrace();
                }
            }
        }

    }
    }


