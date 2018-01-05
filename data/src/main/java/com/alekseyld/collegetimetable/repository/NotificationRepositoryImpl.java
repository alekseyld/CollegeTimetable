package com.alekseyld.collegetimetable.repository;

import android.content.SharedPreferences;

import com.alekseyld.collegetimetable.entity.Notification;
import com.alekseyld.collegetimetable.repository.base.NotificationRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 05.01.2018.
 */

public class NotificationRepositoryImpl implements NotificationRepository {

    private Gson mGson;
    private Type mNotificationListType;
    private SharedPreferences mPref;

    @Inject
    NotificationRepositoryImpl(SharedPreferences sharedPreferences){
        mPref = sharedPreferences;

        mGson = new Gson();
        mNotificationListType = new TypeToken<List<Notification>>(){}.getType();
    }


    @Override
    public List<Notification> getNotifications() {
        String json = mPref.getString(NOTIFICATION_KEY, "");
        return mGson.fromJson(json, mNotificationListType);
    }

    @Override
    public void saveNotifications(List<Notification> notifications) {
        List<Notification> oldNotif = getNotifications();

        if (oldNotif != null)
            notifications.addAll(oldNotif);

        String json = mGson.toJson(notifications);
        SharedPreferences.Editor ed = mPref.edit();
        ed.putString(NOTIFICATION_KEY, json);
        ed.apply();
    }
}
