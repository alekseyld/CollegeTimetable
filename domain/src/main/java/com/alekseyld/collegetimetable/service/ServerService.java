package com.alekseyld.collegetimetable.service;

import com.alekseyld.collegetimetable.entity.TimeTable;

import rx.Observable;

/**
 * Created by Alekseyld on 02.01.2018.
 */

public interface ServerService {

    Observable<TimeTable> getTimetableFromServer(String group);

    //todo объект оповещения
    Observable<Boolean> notification(String login, String password);

    Observable<Boolean> auth(String login, String password);

    Observable<Boolean> changes();

}
