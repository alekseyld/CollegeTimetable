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
    public Observable<TableWrapper> getTimetableFromOnline(boolean online, String group) {

        return urlApi.getUrl(DataUtils.getGroupUrl(group))
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
                            Document document = null;
                            try {
                                document = Jsoup.connect(url.getResult()).get();
                            } catch (IOException e) {
                                return Observable.error(new Error(e.getMessage()));
                            }
                            TableWrapper t = DataUtils.parseDocument(document, group);

                            //Test changes -----------
//                            HashMap<TableWrapper.Lesson, String> test = t.getmTimeTable().get(TableWrapper.Day.Mon);
//                            test.put(TableWrapper.Lesson.lesson0, "Истрория Сафагалеева ");
//                            HashMap<TableWrapper.Day, HashMap<TableWrapper.Lesson, String>> tableWrapper = t.getmTimeTable();
//                            tableWrapper.put(TableWrapper.Day.Mon, test);
//                            t.setTimeTable(tableWrapper);
                            //--------------

                            TableWrapper old = mTimetableRepository.getTimeTable(group);
                            t.setChanges(t.getChanges(old));
                            mTimetableRepository.putTimeTable(t, group);
                            return Observable.just(t);
                        });
        
        /*
        TableWrapper tableWrapper = new TableWrapper();

        HashMap<TableWrapper.Day, String> days = new HashMap<>();
        days.put(TableWrapper.Day.Mon, "Понедельник");
        days.put(TableWrapper.Day.Tue, "Вторник");

        HashMap<TableWrapper.Day, HashMap<TableWrapper.Lesson, String>> table = new HashMap<>();

        HashMap<TableWrapper.Lesson, String> lessons = new HashMap<>();

        lessons.put(TableWrapper.Lesson.lesson1, "Физика");
        lessons.put(TableWrapper.Lesson.lesson2, "Математика");
        lessons.put(TableWrapper.Lesson.lesson3, "История");

        table.put(TableWrapper.Day.Mon, lessons);
        table.put(TableWrapper.Day.Tue, lessons);

        tableWrapper.setDays(days);
        tableWrapper.setTimeTable(table);

        return Observable.just(tableWrapper);*/

        /*return urlApi.getUrl(DataUtils.getGroupUrl(group))
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
                    Document document = null;
                    if(!mSettingsRepository.getGroup().equals(group)){
                        try {
                            document = Jsoup.connect(url.getResult()).get();
                        } catch (IOException e) {
                            return Observable.error(new Error(e.getMessage()));
                        }
                        TableWrapper t = DataUtils.parseDocument(document, group);
                        return Observable.just(t);
                    }

                    if(mSettingsRepository.getUrl() == null){
                        mSettingsRepository.putUrl(url.getResult());
                    }
                    if(online && (url.getStatus() != 2 || url.getStatus() != 3)) {
                        try {
                            document = Jsoup.connect(url.getResult()).get();
                        } catch (IOException e) {
                            return Observable.error(new Error(e.getMessage()));
                        }
                        if(mTimetableRepository.getDocument() == null){
                            mTimetableRepository.putDocument(document);
                            TableWrapper t = DataUtils.parseDocument(document, mSettingsRepository.getGroup());
                            mTimetableRepository.putTimeTable(t, group);
                            return Observable.just(t);
                        }else {
                            if(document.text().equals(mTimetableRepository.getDocument())){
                                return Observable.just(mTimetableRepository.getTimeTable(group));
                            }else {
                                mTimetableRepository.putDocument(document);
                                TableWrapper t = DataUtils.parseDocument(document, mSettingsRepository.getGroup());
                                mTimetableRepository.putTimeTable(t, group);
                                return Observable.just(t);
                            }
                        }
                    }else {
                        if(mTimetableRepository.getDocument() != null){
                            return Observable.just(mTimetableRepository.getTimeTable(group));
                        }else{
                            return Observable.just(new TableWrapper());
                        }
                    }
                });*/
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
