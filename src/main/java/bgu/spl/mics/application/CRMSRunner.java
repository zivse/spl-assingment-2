package bgu.spl.mics.application;
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

/** This is the Main class of Compute Resources Management System application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output a text file.
 */
public class CRMSRunner {
    public static void main(String[] args) throws IOException {
//        JSONArray a = (JSONArray) parser.parse(new FileReader("c:\\exer4-courses.json"));
        String fileName = "src/main/resources/students.json";
        Path path = Paths.get(fileName);

        try (Reader reader = Files.newBufferedReader(path,
                StandardCharsets.UTF_8)) {

            JsonParser parser = new JsonParser();
            JsonElement tree = parser.parse(reader);

            JsonArray array = tree.getAsJsonArray();

            for (JsonElement element: array) {

                if (element.isJsonObject()) {

                    JsonObject car = element.getAsJsonObject();

                    System.out.println("********************");
                    System.out.println(car.get("studentId").getAsLong());
                    System.out.println(car.get("studentName").getAsString());
                }
            }
        }
    }




    public HashMap<String, String> myMethodName() throws FileNotFoundException //check - from https://stackoverflow.com/questions/29965764/how-to-parse-json-file-with-gson
    {
        String path = "absolute path to your file";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        Gson gson = new Gson();
        HashMap<String, String> json = gson.fromJson(bufferedReader, HashMap.class);
        return json;
    }
}
