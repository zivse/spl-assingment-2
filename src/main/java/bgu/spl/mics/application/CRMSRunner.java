package bgu.spl.mics.application;
import bgu.spl.mics.application.objects.*;
import bgu.spl.mics.application.services.StudentService;
import bgu.spl.mics.application.services.TimeService;
import com.google.gson.Gson;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileReader;
import java.util.Iterator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
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
    public static void main(String[] args) throws IOException {
        String fileName = "/Users/zivseker/Desktop/Projects/assignment2/example_input.json";
        Path path = Paths.get(fileName);
        Reader reader = Files.newBufferedReader(path,StandardCharsets.UTF_8);
        JsonParser parser = new JsonParser();
        JsonElement tree = parser.parse(reader);
        JsonObject object = tree.getAsJsonObject();
        JsonArray students = object.get("Students").getAsJsonArray();

        for(JsonElement studentElement : students){
            JsonObject studentObject = studentElement.getAsJsonObject();
            String studentName = studentObject.get("name").getAsString();
            String studentDepartment = studentObject.get("department").getAsString();
            String  studentStatus = studentObject.get("status").getAsString();
            Vector<Model> modelsVector = new Vector<>();
            JsonArray models = studentObject.get("models").getAsJsonArray();
            for(JsonElement model : models){
                JsonObject modelObject = model.getAsJsonObject();
                String modelName = modelObject.get("name").getAsString();
                String modelType = modelObject.get("type").getAsString();
                int modelSize = modelObject.get("size").getAsInt();
                //System.out.println(modelName);
                Data tempModelData = new Data(modelType, 0, modelSize);
                Model tempmodel = new Model(modelName, tempModelData);
                modelsVector.add(tempmodel);
            }
            Student student = new Student(studentName, studentDepartment, studentStatus, modelsVector);
            StudentService studentService = new StudentService(student);
        }
        JsonArray GPUArray = object.get("GPU").getAsJsonArray();
        for (JsonElement gpu : GPUArray){
            GPU Gpu = new GPU(gpu.getAsString());
        }
        JsonArray CPUArray = object.get("CPU").getAsJsonArray();
        for (JsonElement cpu: CPUArray){
            CPU Cpu = new CPU(cpu.getAsInt());
        }
        int tickTime = object.getAsJsonObject("TickTime").getAsInt();
        int duration = object.getAsJsonObject("Duration").getAsInt();
        TimeService timeService = new TimeService(tickTime, duration);



    }
}
