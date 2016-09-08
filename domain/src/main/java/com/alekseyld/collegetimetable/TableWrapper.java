package com.alekseyld.collegetimetable;

import java.util.HashMap;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TableWrapper {

    public enum Lesson{
        lesson0("0"),
        lesson1("1"),
        lesson2("2"),
        lesson3("3"),
        lesson4("4"),
        lesson5("5"),
        lesson6("6");

        private String text;

        Lesson(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }
    }

    public enum Day{
        Mon("Понедельник"),
        Tue("Вторник"),
        Wed("Среда"),
        Thu("Четверг"),
        Friday("Пятница"),
        Saturday("Суббота"),
        Mon2("Понедельник C");

        private String text;

        Day(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }
    }

    HashMap<Day, HashMap<Lesson, String>> mTimeTable;
    HashMap<Day, String> mDays;

    public HashMap<Day, HashMap<Lesson, String>> getmTimeTable() {
        if(mTimeTable != null) {
            return mTimeTable;
        }else {
            return new HashMap<>();
        }
    }

    public void setTimeTable(HashMap<Day, HashMap<Lesson, String>> mTimeTable) {
        this.mTimeTable = mTimeTable;
    }

    public HashMap<Day, String> getDays() {
        if(mDays != null) {
            return mDays;
        }else {
            return new HashMap<>();
        }
    }

    public void setDays(HashMap<Day, String> mDays) {
        this.mDays = mDays;
    }
}
