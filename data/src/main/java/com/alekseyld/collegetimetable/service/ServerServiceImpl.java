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
                .flatMap(authKeyResponce -> {

                    if (!authKeyResponce.contains("error")){

                        return Observable.just(authKeyResponce);
                    } else {
                        String errorText = authKeyResponce.replace("error", "");
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
    public Observable<TimeTable> getTimetableFromServer(String groupOrTeacher) {
        return mServerApi.getTimeTable(groupOrTeacher)
                .onErrorReturn(DataUtils::onErrorReturn)
                .flatMap(timeTable -> {
                    if (timeTable == null)
                        return Observable.error(new UncriticalException("Пользователь не авторизирован"));

                    mTableRepository.putTimeTable(timeTable, groupOrTeacher);
                    return Observable.just(timeTable);
                });
    }

    @Override
    public Observable<TimeTable> getTimetableFromServerDefault() {
        return Observable.just(true)
                .flatMap(b -> getUser())
                .map(User::getGroup)
                .flatMap(this::getTimetableFromServer);
    }

    @Override
    public Observable<List<Notification>> getNewNotifications() {
        return getStudentId()
                .flatMap(studentId -> mServerApi.getNewNotifications(studentId))
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
    public Observable<String> updateChanges() {
        return changes()
                .onErrorReturn(DataUtils::onErrorReturn)
                .flatMap(integer -> {
                    if (integer == null)
                        return Observable.error(new UncriticalException("Ошибка при получении сообщений"));
                    else if (integer == -1)
                        return Observable.error(new UncriticalException("Сервер вернул ошибку"));
                    else if (integer == 1)
                        return getTimetableFromServerDefault();
                    else if (integer == 2)
                        return getNewTimeTableAndNotifications();
                    else if (integer == 3)
                        return getNewTimeTableAndNotifications();
                    else
                        return Observable.just(integer);
                })
                .map(responce -> {
                    if (responce instanceof TimeTable)
                        return "Изменения в расписании";
                    else if (responce instanceof List)
                        return "Новые сообщения";
                    else if (responce instanceof Boolean)
                        return "Изменения в расписании и новые сообщения";
                    else
                        return "";
                });
    }

    @Override
    public Observable<Boolean> getNewTimeTableAndNotifications(){
        return getNewNotifications()
                .flatMap(notifications -> getUser())
                .map(User::getGroup)
                .flatMap(this::getTimetableFromServer)
                .map(timeTable -> true);
    }

    @Override
    public Observable<Integer> changes() {
        return getUserAuthKey()
                .flatMap(authKey -> mServerApi.changes(authKey))
                .onErrorReturn(DataUtils::onErrorReturn)
                .flatMap(change -> {
                    if (change == null)
                        return Observable.error(new UncriticalException("Ошибка при получении изменений"));

                    return Observable.just(change);
                });
    }

    @Override
    public Observable<String> getUserAuthKey(){
        return Observable.just(mUserRepository.getUser())
                .onErrorReturn(DataUtils::onErrorReturn)
                .flatMap(user -> {
                    if (user == null || user.getAuthKey() == null || user.getAuthKey().equals(""))
                        return Observable.error(new UncriticalException("Пользователь не авторизирован"));

                    return Observable.just(user.getAuthKey());
                });
    }

    @Override
    public Observable<String> getStudentId(){
        return Observable.just(mUserRepository.getUser())
                .onErrorReturn(DataUtils::onErrorReturn)
                .flatMap(user -> {
                    if (user == null || user.getStudentId() == null || user.getStudentId().equals(""))
                        return Observable.error(new UncriticalException("Пользователь не авторизирован"));

                    return Observable.just(user.getStudentId());
                });
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

    @Override
    public Observable<Integer> certificate(int type, int count, String fio, String group, String studentid, String district) {
        return getUserAuthKey()
                .flatMap(authkey -> mServerApi.certificate(authkey, type, count, fio, group, studentid, district))
                .onErrorReturn(DataUtils::onErrorReturn);
    }
}