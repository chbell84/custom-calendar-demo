package com.example.customcalendardemo.services;

import com.example.customcalendardemo.domain.CCEvent;
import com.example.customcalendardemo.domain.CCalendar;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarExporter {
    private LocalDate date;
    private List<DayOfWeek> weekdays;
    private List<LocalDate> excludeDays;
    private java.util.Calendar tempCal;
    private UidGenerator ug;

    public CalendarExporter(Calendar startDay, List<DayOfWeek> cycleDays, List<LocalDate> excludeList){
        try {
            ug = new UidGenerator("1");
        } catch (SocketException e) {
            e.printStackTrace();
        }
        weekdays = cycleDays;
        excludeDays=excludeList;
        tempCal = startDay;

        date = LocalDate.of(startDay.get(Calendar.YEAR),startDay.get(Calendar.MONTH),startDay.get(Calendar.DAY_OF_MONTH));
    }

    private net.fortuna.ical4j.model.Calendar build(CCalendar psudoCal) {
        net.fortuna.ical4j.model.Calendar cal = new net.fortuna.ical4j.model.Calendar();
        cal.getProperties().add(new ProdId("-//Charles Bell//trying something//EN"));
        cal.getProperties().add(Version.VERSION_2_0);
        cal.getProperties().add(CalScale.GREGORIAN);
        //tempCal = new GregorianCalendar();
        LocalDate futureDate = date;
        for(int i=0;i<psudoCal.getTotalCycles();i++) {
            for(String l : psudoCal.getDayLabels()) {
                //avoid weekends and holidays
                while(!weekdays.contains(futureDate.getDayOfWeek())||excludeDays.contains(futureDate)) futureDate=futureDate.plusDays(1);
                tempCal.clear();
                tempCal.set(futureDate.getYear(),futureDate.getMonthValue()-1,futureDate.getDayOfMonth());
                Date d = new Date(tempCal.getTime());
                VEvent day = new VEvent(d,l);

                // Generate a UID for the event..
                day.getProperties().add(ug.generateUid());
                cal.getComponents().add(day);
                for(CCEvent e:psudoCal.getCycles()[i].get(l).values()) cal.getComponents().add(e.toVEvent((Calendar) tempCal.clone()));

                //increment the day
                futureDate=futureDate.plusDays(1);
            }
        }
        return cal;
    }

    public void export(String filename, CCalendar psudoCal) {
        //build();
        try {
            FileOutputStream fout = new FileOutputStream(filename);

            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(build(psudoCal), fout);
        }
        catch(FileNotFoundException e) {
            System.out.print("File Not Found");
        } catch (IOException | ValidationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void export(CCalendar psudoCal){
        export("MyCalendar.ics",psudoCal);
    }

    public void setExcludeDays(ArrayList<LocalDate> excludeDays) {
        this.excludeDays=excludeDays;
    }

    public void addExcludeDay(LocalDate parse) {
        excludeDays.add(parse);
    }

}
