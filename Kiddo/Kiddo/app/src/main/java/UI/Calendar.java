package UI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.sql.* ;
import java.text.SimpleDateFormat;

/**
 * Created by Jason Qian on 13/11/2016.
 */

public class Calendar {
    //not sure what to do with this, might be using a framework instead
    private ArrayList<CalendarEvent> events = new ArrayList<CalendarEvent>();

    public void addEvent(CalendarEvent event){
        this.events.add(event);
    }

    public ArrayList<CalendarEvent> getEvents(){
        Collections.sort(this.events);
        return this.events;
    }
}
