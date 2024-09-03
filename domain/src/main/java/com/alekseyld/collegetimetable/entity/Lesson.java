package com.alekseyld.collegetimetable.entity;

import org.jetbrains.annotations.Nullable;

/**
 * Created by Alekseyld on 16.09.2017.
 */

public class Lesson {

    private int number;

    private String name;

    @Nullable
    private String secondName;

    private String teacher;

    private String time;

    private boolean change;

    public int getNumber() {
        return number;
    }

    public Lesson setNumber(int number) {
        this.number = number;
        return this;
    }

    public String getDoubleName() {
        if (secondName != null) {
            return name + "\n/\n" +
                    secondName;
        } else {
            return name;
        }
    }

    public String getName(){
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

    public String getSecondName() {
        return secondName;
    }

    public Lesson setSecondName(@Nullable String secondName) {
        this.secondName = secondName;
        return this;
    }

    public String getTime() {
        return time;
    }

    public Lesson setTime(String time) {
        this.time = time;
        return this;
    }
}
