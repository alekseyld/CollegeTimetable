package com.alekseyld.collegetimetable.service;

import android.text.Editable;
import android.util.Log;

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

public class TableServiceImpl implements TableService{

    private TableRepository mTimetableRepository;
    private SettingsRepository mSettingsRepository;

    private ProxyApi urlApi;


    @Inject
    TableServiceImpl(TableRepository tableRepository, SettingsRepository settingsRepository, Retrofit restAdapter){
        mSettingsRepository = settingsRepository;
        mTimetableRepository = tableRepository;
        urlApi = restAdapter.create(ProxyApi.class);
    }

    @Override
    public Observable<TableWrapper> getTimetable(boolean online) {
        return urlApi.getUrl(DataUtils.getGroupUrl(mSettingsRepository.getGroup()))
                .onErrorReturn((error) ->{
                    ApiResponse apiResponse = new ApiResponse();
                    if(error instanceof UnknownHostException){
                        apiResponse.setStatus(2);
                    }else {
                        apiResponse.setStatus(3);
                    }
                    return apiResponse;
                })
                .flatMap(url -> {
//                    Log.d("TimeTableUrl", mSettingsRepository.getGroup());
//                    Log.d("ApiStatus", "Api status - "+url.getStatus());
                    Document document = null;
                    if(online && (url.getStatus() != 2 || url.getStatus() != 3)) {
                        try {
                            document = Jsoup.connect(url.getResult()).get();
                        } catch (IOException e) {
                            return Observable.error(new Error(e.getMessage()));
                        }
                        if(mTimetableRepository.getDocument() == null){
                            mTimetableRepository.putDocument(document);
                            TableWrapper t = DataUtils.parseDocument(document, mSettingsRepository.getGroup());
                            mTimetableRepository.putTimeTable(t);
                            return Observable.just(t);
                        }else {
                            if(document.text().equals(mTimetableRepository.getDocument())){
                                return Observable.just(mTimetableRepository.getTimeTable());
                            }else {
                                mTimetableRepository.putDocument(document);
                                TableWrapper t = DataUtils.parseDocument(document, mSettingsRepository.getGroup());
                                mTimetableRepository.putTimeTable(t);
                                return Observable.just(t);
                            }
                        }
                    }else {
                        if(mTimetableRepository.getDocument() != null){
                            return Observable.just(mTimetableRepository.getTimeTable());
                        }else{
                            return Observable.just(new TableWrapper());
                        }
                    }
                });
    }

    @Override
    public Observable<Boolean> update(String minute, String group) {
        return null;
    }
}
