package com.alekseyld.collegetimetable.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TimeTable {

    private Date lastRefresh;

    /* Index default
     * 0 - Понедельник
     * 1 - Вторник
     * 2 - Среда
     * 3 - Четверг
     * 4 - Пятница
     * 5 - Суббота
     * 6 - Понедельник следующей недели
     */
    private List<Day> dayList = new ArrayList<>();

    private String group;

    public Date getLastRefresh() {
        return lastRefresh;
    }

    public TimeTable setLastRefresh(Date lastRefresh) {
        this.lastRefresh = lastRefresh;
        return this;
    }

    public List<Day> getDayList() {
        return dayList;
    }

    public TimeTable setDayList(List<Day> dayList) {
        this.dayList = dayList;
        return this;
    }

    public TimeTable addDay(Day day){
        dayList.add(day);
        return this;
    }

    public String getGroup() {
        return group;
    }

    public TimeTable setGroup(String group) {
        this.group = group;
        return this;
    }
}
