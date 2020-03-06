package com.example.customcalendardemo.domain;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;

import java.util.Calendar;
/*
* consider using Java.time package
* LocalTime class
* */
//import java.util.Date;

public class CCEvent {
    private long time;
    private long duration;
    private String eventName;
    private String description;
    private Uid uid;
    private int sHr;
    private int sMin;
    private int sSec;
    private int eHr;
    private int eMin;
    private int eSec;
    private PropertyList pList;

    CCEvent(Uid uid, int startHr, int startMin, int startSec, int endHr, int endMin, int endSec, String name){
        sHr = startHr;
        sMin = startMin;
        sSec = startSec;
        eHr = endHr;
        eMin = endMin;
        eSec = endSec;
        eventName = name;
        this.uid = uid;
        pList = new PropertyList();
        pList.add(uid);
        pList.add(new Summary(name));
    }
    CCEvent(Uid uid, int startHr, int startMin, int startSec, int endHr, int endMin, int endSec, String name, String description){
        this(uid, startHr, startMin, startSec, endHr, endMin, endSec, name);
        this.description = description;
        pList.add(new Description(description));
    }
    public VEvent toVEvent(Calendar cal){
        cal.set(Calendar.HOUR_OF_DAY,sHr);
        cal.set(Calendar.MINUTE,sMin);
        cal.set(Calendar.SECOND,sSec);
        DateTime start = new DateTime(cal.getTime());
        cal.set(Calendar.HOUR_OF_DAY,eHr);
        cal.set(Calendar.MINUTE,eMin);
        cal.set(Calendar.SECOND,eSec);
        DateTime end = new DateTime(cal.getTime());
        VEvent out = new VEvent(start,end,eventName);
        out.getProperties().add(new Description(description));
        out.getProperties().add(uid);
        return out;
    }
    PropertyList getProperties(){
        return pList;
    }
    String getDescription(){
        return description;
    }
    String getEventName(){
        return eventName;
    }
    Uid getUid(){
        return uid;
    }
}
