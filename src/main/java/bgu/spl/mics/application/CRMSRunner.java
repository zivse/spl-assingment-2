package bgu.spl.mics.application;
import bgu.spl.mics.application.objects.*;
import bgu.spl.mics.application.services.GPUService;
import bgu.spl.mics.application.services.StudentService;
import bgu.spl.mics.application.services.TimeService;
import com.google.gson.*;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

/** This is the Main class of Compute Resources Management System application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output a text file.
 */
public class CRMSRunner {
    public static void main(String[] args) {
        String fileName = "./example_input.json";//Users/zivseker/Desktop/Projects/assignment2/example_input.json";  //";C://Users//nir42//Downloads/example_input.json
        Path path = Paths.get(fileName);
        Reader reader = null;
        try{
            reader = Files.newBufferedReader(path,StandardCharsets.UTF_8);
        }
        catch (IOException ignored) {
        };
        JsonParser parser = new JsonParser();
        JsonElement tree = parser.parse(reader);
        JsonObject object = tree.getAsJsonObject();
        JsonArray students = object.get("Students").getAsJsonArray();

int counterStudent=0;//for test
        for(JsonElement studentElement : students){
            JsonObject studentObject = studentElement.getAsJsonObject();
            String studentName = studentObject.get("name").getAsString();
            String studentDepartment = studentObject.get("department").getAsString();
            String  studentStatus = studentObject.get("status").getAsString();
            Vector<Model> modelsVector = new Vector<>();
            Student student = new Student(studentName, studentDepartment, studentStatus, modelsVector);
            if(counterStudent==0){ //for test
            StudentService studentService = new StudentService(student);
            Thread studentServiceThread = new Thread(studentService);
                studentServiceThread.start();
                counterStudent++;} //for test
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
        int counter=0; //for tests
        Cluster cluster=new Cluster();
        JsonArray GPUArray = object.get("GPUS").getAsJsonArray();
        // System.out.println(GPUArray.toString());
        for (JsonElement gpu : GPUArray){
            GPU Gpu = new GPU(gpu.getAsString());
            cluster.addGPU(Gpu);
            if(counter==0) { //for test
                GPUService testService = new GPUService("test", Gpu);
                Thread test = new Thread(testService);
                test.start();
                counter++;
            }
        }

        JsonArray CPUArray = object.get("CPUS").getAsJsonArray();
        for (JsonElement cpu: CPUArray){
            CPU Cpu = new CPU(cpu.getAsInt());
            cluster.addCPU(Cpu);
        }
        JsonArray ConferencesArray = object.get("Conferences").getAsJsonArray();
        for(JsonElement conference : ConferencesArray){
            JsonObject conferenceObject = conference.getAsJsonObject();
            String conferenceName = conferenceObject.get("name").getAsString();
            int  conferenceDate = conferenceObject.get("date").getAsInt();
            ConfrenceInformation tempConf = new ConfrenceInformation(conferenceName, conferenceDate);
        }
        int tickTime = object.get("TickTime").getAsInt();
        int duration = object.get("Duration").getAsInt();
        TimeService timeService = new TimeService(tickTime, duration);
        Thread test2=new Thread(timeService);
        test2.start();
    }
}