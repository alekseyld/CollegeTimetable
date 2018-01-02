package com.alekseyld.collegetimetable.service;

import com.alekseyld.collegetimetable.api.ServerApi;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.exception.UncriticalException;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Alekseyld on 02.01.2018.
 */

public class ServerServiceImpl implements ServerService{

    private ServerApi serverApi;

    @Inject
    ServerServiceImpl(@Named("server") Retrofit restAdapter) {
        serverApi = restAdapter.create(ServerApi.class);
    }

    @Override
    public Observable<TimeTable> getTimetableFromServer() {
        return null;
    }

    @Override
    public Observable<Boolean> notification(String login, String password) {
        return null;
    }

    @Override
    public Observable<Boolean> auth(String login, String password) {
        return serverApi.auth(login, password)
                .flatMap(response -> {

                    if (!response.contains("error")){

                        //todo сохранять authkey
                        String authKey = response;

                        return Observable.just(true);
                    } else {
                        String errorText = response.replace("error", "");
                        return Observable.error(new UncriticalException(errorText));
                    }
                });
    }

    @Override
    public Observable<Boolean> changes() {
        return null;
    }
}
