package com.alekseyld.collegetimetable.service;

import com.alekseyld.collegetimetable.entity.TimeTable;

import rx.Observable;

/**
 * Created by Alekseyld on 02.09.2017.
 */

public interface TableService {

    Observable<TimeTable> getTimetableFromOnline(boolean online, String group);
    Observable<TimeTable> getTimetableFromOffline(String group);

    Observable<Boolean> saveTimetable(TimeTable tableTable, String group);

    Observable<Boolean> update(String minute, String group);

}
