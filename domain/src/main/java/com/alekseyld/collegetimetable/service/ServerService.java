package com.alekseyld.collegetimetable.service;

import com.alekseyld.collegetimetable.entity.Notification;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.entity.User;

import java.util.List;

import rx.Observable;

/**
 * Created by Alekseyld on 02.01.2018.
 */

public interface ServerService {

    Observable<TimeTable> getTimetableFromServer(String groupOrTeacher);

    Observable<User> getUser(String authKey);

    Observable<TimeTable> getTimetableFromServerDefault();

    //todo объект оповещения
    Observable<List<Notification>> getNewNotifications();

    Observable<Boolean> auth(String login, String password);

    Observable<Boolean> getNewTimeTableAndNotifications();

    Observable<Integer> changes();

    Observable<String> getUserAuthKey();

    Observable<String> getStudentId();

    //todo вынести в UserService
    Observable<Boolean> deleteUser();
    Observable<User> getUser();

    Observable<List<Notification>> getLocalNotifications();

    Observable<String> updateChanges();

    Observable<Integer> certificate(int type, int count, String  fio, String  group, String  studentid, String  district);
}
