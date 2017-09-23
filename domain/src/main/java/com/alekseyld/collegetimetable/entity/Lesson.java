package com.alekseyld.collegetimetable.entity;

/**
 * Created by Alekseyld on 16.09.2017.
 */

public class Lesson {

    private int number;

    private String name;

    private String teacher;

    private boolean change;

    public int getNumber() {
        return number;
    }

    public Lesson setNumber(int number) {
        this.number = number;
        return this;
    }

    public String getName() {
        return name;
    }

    public Lesson setName(String name) {
        this.name = name;
        return this;
    }

    public String getTeacher() {
        return teacher;
    }

    public Lesson setTeacher(String teacher) {
        this.teacher = teacher;
        return this;
    }

    public boolean isChange() {
        return change;
    }

    public Lesson setChange(boolean change) {
        this.change = change;
        return this;
    }
}
