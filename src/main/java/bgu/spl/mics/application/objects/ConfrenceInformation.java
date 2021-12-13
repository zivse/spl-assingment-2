package bgu.spl.mics.application.objects;

/**
 * Passive object representing information on a conference.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class ConfrenceInformation {
    private int time;
    private String name;
    private int date;
    public ConfrenceInformation(String _name,int _date){
        date=_date;
        name=_name;
        time =1;
    }
    public void updateTime(){
        time++;
    }
}
