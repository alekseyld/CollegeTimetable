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

    private HashMap<Day, HashMap<Lesson, String>> mTimeTable;
    private HashMap<Day, HashMap<Lesson, Boolean>> mChanges;
    private HashMap<Day, String> mDays;

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

    public boolean equals(TableWrapper tableWrapper) {
        boolean size = mTimeTable.size() == tableWrapper.mTimeTable.size();
        boolean keys = mTimeTable.keySet().equals(mTimeTable.keySet());
        if(size && keys) {
            for (Day d: mTimeTable.keySet()) {
                for (Lesson l: mTimeTable.get(d).keySet()){
                    if(!mTimeTable.get(d).get(l).equals(tableWrapper.getmTimeTable().get(d).get(l))){
                        return false;
                    }
                }
            }
            return true;
        }else {
            return false;
        }
    }

    public HashMap<Day, HashMap<Lesson, Boolean>> getChanges() {
        return mChanges;
    }

    public void setChanges(HashMap<Day, HashMap<Lesson, Boolean>> mChanges) {
        this.mChanges = mChanges;
    }

    /*
     * true - if have changes
     */
    public boolean isChanges(){
        if(mChanges != null){
            for(Day d: mChanges.keySet())
                for (Lesson l: mChanges.get(d).keySet())
                    if(mChanges.get(d).get(l)){
                        return true;
                    }
        }
        return false;
    }

    public HashMap<Day, HashMap<Lesson, Boolean>> getChanges(TableWrapper tableWrapper){
        if(tableWrapper != null
                && tableWrapper.getmTimeTable() != null
                && tableWrapper.getmTimeTable().size() > 0
                && mTimeTable != null) {

            HashMap<Day, HashMap<Lesson, Boolean>> changes = new HashMap<>();
            HashMap<Lesson, Boolean> dayChange;
            for (Day d : mTimeTable.keySet()) {
                dayChange = new HashMap<>();
                for (Lesson l : mTimeTable.get(d).keySet()) {
                    if (!mTimeTable.get(d).get(l).equals(tableWrapper.getmTimeTable().get(d).get(l))) {
                        dayChange.put(l, true);
                    } else {
                        dayChange.put(l, false);
                    }
                }
                changes.put(d, dayChange);
            }
            return changes;
        }
        return null;
    }

}
