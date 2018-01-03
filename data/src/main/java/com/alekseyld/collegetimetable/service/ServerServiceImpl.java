package com.alekseyld.collegetimetable.service;

import com.alekseyld.collegetimetable.api.ServerApi;
import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.entity.User;
import com.alekseyld.collegetimetable.exception.UncriticalException;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;
import com.alekseyld.collegetimetable.repository.base.TableRepository;
import com.alekseyld.collegetimetable.repository.base.UserRepository;

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

    @Inject
    ServerServiceImpl(@Named("server") Retrofit restAdapter,
                      UserRepository userRepository,
                      SettingsRepository settingsRepository,
                      TableRepository tableRepository) {

        mServerApi = restAdapter.create(ServerApi.class);
        mUserRepository = userRepository;
        mSettingsRepository = settingsRepository;
        mTableRepository = tableRepository;

    }

    @Override
    public Observable<TimeTable> getTimetableFromServer(String group) {
        return mServerApi.getTimeTable(group)
                .onErrorReturn(throwable -> null)
                .flatMap(timeTable -> {
                    if (timeTable == null)
                        return Observable.error(new UncriticalException("Пользователь не авторизирован"));

                    mTableRepository.putTimeTable(timeTable, group);
                    return Observable.just(timeTable);
                });
    }

    @Override
    public Observable<Boolean> notification(String login, String password) {
        //todo получение обновлений
        return null;
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

    private Observable<User> getUser(String authKey){
        return mServerApi.getUser(authKey)
                .onErrorReturn(error -> null)
                .flatMap(user -> {
                    if (user == null)
                        return Observable.error(new UncriticalException("Ошибка при получении пользователя"));

                    user.setAuthKey(authKey);
                    return Observable.just(user);
                });
    }

    @Override
    public Observable<Boolean> changes() {
        //todo есть ли обновления
        return null;
    }
}
