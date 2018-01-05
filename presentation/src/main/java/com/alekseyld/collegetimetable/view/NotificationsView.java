package com.alekseyld.collegetimetable.view;

import com.alekseyld.collegetimetable.entity.Notification;

import java.util.List;

/**
 * Created by Alekseyld on 04.01.2018.
 */

public interface NotificationsView extends BaseView {

    void setNotification(List<Notification> notification);
    void addNotification(List<Notification> notification);

}
