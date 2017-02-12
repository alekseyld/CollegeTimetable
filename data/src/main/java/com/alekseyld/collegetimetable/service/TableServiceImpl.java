package com.alekseyld.collegetimetable.service;

import com.alekseyld.collegetimetable.TableWrapper;
import com.alekseyld.collegetimetable.api.ProxyApi;
import com.alekseyld.collegetimetable.entity.ApiResponse;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;
import com.alekseyld.collegetimetable.repository.base.TableRepository;
import com.alekseyld.collegetimetable.utils.DataUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TableServiceImpl implements TableService {

    private TableRepository mTimetableRepository;
    private SettingsRepository mSettingsRepository;

    private ProxyApi urlApi;


    @Inject
    TableServiceImpl(TableRepository tableRepository, SettingsRepository settingsRepository, Retrofit restAdapter) {
        mSettingsRepository = settingsRepository;
        mTimetableRepository = tableRepository;
        urlApi = restAdapter.create(ProxyApi.class);
    }

    private Observable<Document> connectAndGetData(String group) {
        return urlApi.getUrl(DataUtils.getGroupUrl(group))
                .onErrorReturn((error) ->{
                    ApiResponse apiResponse = new ApiResponse();

                    if(error instanceof UnknownHostException){
                        apiResponse.setStatus(2);
                    }else {
                        apiResponse.setStatus(3);
                    }

                    return apiResponse;
                }).map(apiResponse -> {
                    Document document;

                    try {
                        document = Jsoup.connect(apiResponse.getResult()).timeout(5000).get();
                    } catch (IOException e) {
                        document = null;
                    }

                    return document;
                }).map(document -> {
                    if (document != null)
                        return document;

                    try {
                        document = Jsoup.connect(DataUtils.getGroupUrl(group)).timeout(5000).get();
                    } catch (IOException e) {
                        document = null;
                    }

                    return document;
                });
    }

    @Override
    public Observable<TableWrapper> getTimetableFromOnline(boolean online, String group) {

        return connectAndGetData(group).flatMap(document -> {
            if(document == null)
                return Observable.error(new Error("Произошла ошибка при подключении"));

            TableWrapper t = DataUtils.parseDocument(document, group);
            TableWrapper old = mTimetableRepository.getTimeTable(group);
            t.setChanges(t.getChanges(old));
            mTimetableRepository.putTimeTable(t, group);
            return Observable.just(t);
        });
    }

    @Override
    public Observable<TableWrapper> getTimetableFromOffline(String group) {
        return Observable.just(
                mTimetableRepository.getTimeTable(group)
        );
    }

    @Override
    public Observable<Boolean> saveTimetable(TableWrapper tableTable, String group) {
        return Observable.just(
                mTimetableRepository.putTimeTable(tableTable, group)
        );
    }

    @Override
    public Observable<Boolean> update(String minute, String group) {
        return null;
    }
}
