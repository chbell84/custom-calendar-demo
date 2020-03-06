package com.example.customcalendardemo.domain;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

/**
 * @author Charles Henry Bell
 *
 */
public class CCalendar {
	private int totalCycles;
	private HashMap<String, HashMap<Uid, CCEvent>>[] cycles;
	private String[] dayLabels;

	public CCalendar(int numCycles, String[] labels){
		totalCycles = numCycles;
		cycles = new HashMap[numCycles];
		dayLabels = labels;
		for(int i=0;i<numCycles;i++){
			cycles[i] = new HashMap<String, HashMap<Uid, CCEvent>>();
			for(String s:labels){
				cycles[i].put(s, new HashMap<Uid, CCEvent>());
			}
		}
	}
	public String[] getDayLabels(){ return dayLabels;}
	public HashMap<String, HashMap<Uid, CCEvent>>[] getCycles(){ return cycles;};
	public int getTotalCycles() {
		return totalCycles;
	}
	public void addEvent(String CDay, int cycle, CCEvent e){
		cycles[cycle].get(CDay).put(e.getUid(),e);
	}
	public void removeEvent(String CDay, int cycle,  CCEvent e){
		cycles[cycle].get(CDay).remove(e.getUid());
	}
	public void removeEvent(String CDay, int cycle,  Uid u){
		cycles[cycle].get(CDay).remove(u);
	}
	public HashMap getEvents(String CDay, int cycle){
		return cycles[cycle].get(CDay);
	}
}