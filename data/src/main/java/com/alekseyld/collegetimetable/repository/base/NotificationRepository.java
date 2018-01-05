package com.alekseyld.collegetimetable.repository.base;

import com.alekseyld.collegetimetable.entity.Notification;

import java.util.List;

/**
 * Created by Alekseyld on 05.01.2018.
 */

public interface NotificationRepository {

    String NOTIFICATION_KEY = "NOTIFICATION_KEY";

    List<Notification> getNotifications();
    void saveNotifications(List<Notification> notifications);

}
