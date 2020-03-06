package com.example.customcalendardemo.controllers;

import com.example.customcalendardemo.domain.CCalendar;
import com.example.customcalendardemo.domain.Greeting;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TestController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/test")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/calendar")
    public CCalendar calendar(@RequestParam(value = "TimeZone", defaultValue = "America/New_York") String tz, @RequestParam(value = "Duration", defaultValue = "10") int length) {
        //java.util.Calendar startDate = GregorianCalendar.getInstance(TimeZoneRegistryFactory.getInstance().createRegistry().getTimeZone(tz));
        String [] labels = {"a","b","c","d","e"};
        return new CCalendar(length, labels);
    }
}
