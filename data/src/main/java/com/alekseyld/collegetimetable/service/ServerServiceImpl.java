package com.alekseyld.collegetimetable.service;

import com.alekseyld.collegetimetable.api.ServerApi;
import com.alekseyld.collegetimetable.entity.Notification;
import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.entity.User;
import com.alekseyld.collegetimetable.exception.UncriticalException;
import com.alekseyld.collegetimetable.repository.base.NotificationRepository;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;
import com.alekseyld.collegetimetable.repository.base.TableRepository;
import com.alekseyld.collegetimetable.repository.base.UserRepository;
import com.alekseyld.collegetimetable.utils.DataUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Alekseyld on 02.01.2018.
 */

public class ServerServiceImpl implements ServerService{

    private ServerApi mServerApi;

    private UserRepository mUserRepository;
    private SettingsRepository mSettingsRepository;
    private TableRepository mTableRepository;
    private NotificationRepository mNotificationRepository;

    @Inject
    ServerServiceImpl(@Named("server") Retrofit restAdapter,
                      UserRepository userRepository,
                      SettingsRepository settingsRepository,
                      TableRepository tableRepository,
                      NotificationRepository notificationRepository) {

        mServerApi = restAdapter.create(ServerApi.class);
        mUserRepository = userRepository;
        mSettingsRepository = settingsRepository;
        mTableRepository = tableRepository;
        mNotificationRepository = notificationRepository;

    }

    @Override
    public Observable<Boolean> auth(String login, String password) {
        return mServerApi.auth(login, password)
                .flatMap(response -> {

                    if (!response.contains("error")){

                        String authKey = response;
                        return Observable.just(authKey);
                    } else {
                        String errorText = response.replace("error", "");
                        return Observable.error(new UncriticalException(errorText));
                    }
                })
                .flatMap(this::getUser)
                .map(user -> {
                    mUserRepository.putUser(user);

                    if (user.getGroup() != null && !user.getGroup().equals("")) {
                        Settings settings = mSettingsRepository.getSettings();

                        if (!settings.getNotificationGroup().equals(user.getGroup())) {
                            settings.setNotificationGroup(user.getGroup());
                            mSettingsRepository.saveSettings(settings);
                        }
                    }

                    return true;
                });
    }

    @Override
    public Observable<User> getUser(String authKey){
        return mServerApi.getUser(authKey)
                .onErrorReturn(DataUtils::onErrorReturn)
                .flatMap(user -> {
                    if (user == null)
                        return Observable.error(new UncriticalException("Ошибка при получении пользователя"));

                    user.setAuthKey(authKey);
                    return Observable.just(user);
                });
    }

    @Override
    public Observable<TimeTable> getTimetableFromServer(String group) {
        return mServerApi.getTimeTable(group)
                .onErrorReturn(DataUtils::onErrorReturn)
                .flatMap(timeTable -> {
                    if (timeTable == null)
                        return Observable.error(new UncriticalException("Пользователь не авторизирован"));

                    mTableRepository.putTimeTable(timeTable, group);
                    return Observable.just(timeTable);
                });
    }

    @Override
    public Observable<List<Notification>> getNewNotifications() {
        //todo получение обновлений
        return Observable.just(mUserRepository.getUser())
                .onErrorReturn(DataUtils::onErrorReturn)
                .flatMap(user -> {
                    if (user == null || user.getAuthKey() == null || user.getAuthKey().equals(""))
                        return Observable.error(new UncriticalException("Пользователь не авторизирован"));

                    return mServerApi.getNewNotifications(user.getAuthKey());
                })
                .onErrorReturn(DataUtils::onErrorReturn)
                .flatMap(notifications -> {
                    if (notifications == null)
                        return Observable.error(new UncriticalException("Ошибка при получении сообщений"));

                    List<Notification> notificationList = new ArrayList<>(Arrays.asList(notifications));
                    mNotificationRepository.saveNotifications(notificationList);

                    return Observable.just(notificationList);
                });
    }

    @Override
    public Observable<List<Notification>> getLocalNotifications() {
        return Observable.just(
                mNotificationRepository.getNotifications()
        );
    }

    @Override
    public Observable<Boolean> changes() {
        //todo есть ли обновления
        return null;
    }

    @Override
    public Observable<Boolean> deleteUser() {
        return Observable.just(
                mUserRepository.deleteUser()
        );
    }

    @Override
    public Observable<User> getUser() {
        return Observable.just(
                mUserRepository.getUser()
        );
    }

}