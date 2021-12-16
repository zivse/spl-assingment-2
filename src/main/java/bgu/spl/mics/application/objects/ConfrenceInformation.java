package bgu.spl.mics.application.objects;

import java.util.HashMap;
import java.util.Vector;

/**
 * Passive object representing information on a conference.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class ConfrenceInformation {
    private int time;
    private String name;
    private int date;
    private int totalPublishers;
    private HashMap<Student, Vector<String>> ConnectStudentToArticles;
    public ConfrenceInformation(String _name,int _date){
        date=_date;
        name=_name;
        time =1;
      ConnectStudentToArticles = new HashMap<Student, Vector<String>>();
    }
    public void setConnectStudentToArticles(Student student,Model model){
        if(ConnectStudentToArticles.get(student)==null){
            Vector<String> modelsNames=new Vector<String>();
            ConnectStudentToArticles.put(student,modelsNames);
            modelsNames.add(model.getName());
            totalPublishers=totalPublishers+1;
        }
        else{
            ConnectStudentToArticles.get(student).add(model.getName());
            totalPublishers=totalPublishers+1;
        }
    }
    public void updateTime(){
        time++;
    }
    public int getTime(){
        return time;
    }
    public int getDate(){
        return date;
    }
    public HashMap<Student, Vector<String>>getConnectStudentToArticles(){
        return ConnectStudentToArticles;
    }

    public int getTotalPublishers() {
        return totalPublishers;
    }
    public String toString(){
        String confrenceString = "";
        confrenceString += "conferences: " + "\n" + "name: " + name + "\n" + "date" + date + "\n" + "publications" +  "\n"+ ConnectStudentToArticles.toString();
        return confrenceString;
    }

    public String getName() {
        return name;
    }
}
