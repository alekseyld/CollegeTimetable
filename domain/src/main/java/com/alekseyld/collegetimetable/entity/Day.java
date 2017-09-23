package com.alekseyld.collegetimetable.entity;

import java.util.List;

/**
 * Created by Alekseyld on 21.09.2017.
 */

public class Day {

    private int id;

    private String date;

    private List<Lesson> dayLessons;

    public String getDate() {
        return date;
    }

    public Day setDate(String date) {
        this.date = date;
        return this;
    }

    public int getId() {
        return id;
    }

    public Day setId(int id) {
        this.id = id;
        return this;
    }

    public List<Lesson> getDayLessons() {
        return dayLessons;
    }

    public Day setDayLessons(List<Lesson> dayLessons) {
        this.dayLessons = dayLessons;
        return this;
    }
}
