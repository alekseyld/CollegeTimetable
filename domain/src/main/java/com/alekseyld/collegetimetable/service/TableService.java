package com.alekseyld.collegetimetable.service;

import com.alekseyld.collegetimetable.TableWrapper;

import rx.Observable;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public interface TableService {
    Observable<TableWrapper> getTimetable(boolean online, String group);
    Observable<Boolean> update(String minute, String group);
}
