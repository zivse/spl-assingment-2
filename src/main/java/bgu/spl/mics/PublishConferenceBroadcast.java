package bgu.spl.mics;
import bgu.spl.mics.application.objects.Student;

import java.util.HashMap;
import java.util.Vector;

public class PublishConferenceBroadcast implements Broadcast{
    private HashMap<Student, Vector<String>>ConnectStudentToArticles;
    private int totalPublishers;
    public PublishConferenceBroadcast(HashMap<Student, Vector<String>>_ConnectStudentToArticles,int _totalPublishers){
        ConnectStudentToArticles=_ConnectStudentToArticles;
        totalPublishers=_totalPublishers;
    }
    public HashMap<Student, Vector<String>> getConnectStudentToArticles(){
        return ConnectStudentToArticles;
    }
    public int getTotalPublishers(){
        return totalPublishers;
    }
}
