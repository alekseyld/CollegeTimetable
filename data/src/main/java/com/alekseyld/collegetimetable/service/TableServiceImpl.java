package com.alekseyld.collegetimetable.service;

import com.alekseyld.collegetimetable.api.ProxyApi;
import com.alekseyld.collegetimetable.entity.ApiResponse;
import com.alekseyld.collegetimetable.entity.Day;
import com.alekseyld.collegetimetable.entity.Lesson;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.exception.UncriticalException;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;
import com.alekseyld.collegetimetable.repository.base.TableRepository;
import com.alekseyld.collegetimetable.utils.DataUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
                .onErrorReturn((error) -> {
                    ApiResponse apiResponse = new ApiResponse();

                    if (error instanceof UnknownHostException) {
                        apiResponse.setStatus(2);
                    } else {
                        apiResponse.setStatus(3);
                    }

                    return apiResponse;
                }).map(apiResponse -> {
                    Document document;

                    try {
                        document = Jsoup.connect(apiResponse.getResult()).timeout(5000).get();
                    } catch (IOException | IllegalArgumentException e) {
                        e.printStackTrace();

                        document = null;
                    }

                    return document;
                }).map(document -> {
                    if (document != null && !document.title().equals("NoBlockMe.ru - бесплатный анонимайзер для ВКонтакте и Одноклассники")
                            && document.html().contains("<body bgcolor=\"bbbbbb\">"))
                        return document;

                    try {
                        for (int i = 0; i < 5; i++) {
                            document = Jsoup.connect(DataUtils.getGroupUrl(group)).timeout(5000).get();
                            if (document != null)
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();

                        document = null;
                    }

                    return document;
                }).map(document -> {
                    if (document != null && document.html().contains("<body bgcolor=\"bbbbbb\">"))
                        return document;

                    try {
                        document = Jsoup.connect(DataUtils.getGroupUrl("http://109.195.146.243/wp-content/uploads/time/", group)).timeout(5000).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                        document = null;
                    }

                    return document;
                }).flatMap(document -> {
                    if (document == null || !document.html().contains("<body bgcolor=\"bbbbbb\">")) {
                        return Observable.error(new UncriticalException("Не удалось подключиться к сайту (0:1)"));
                    }

                    return Observable.just(document);
                });
    }

    @Override
    public Observable<TimeTable> getTimetableFromOnline(boolean online, String group) {

        return connectAndGetData(group).flatMap(document -> {
            if (document == null)
                return Observable.error(new UncriticalException("Не удалось подключиться к сайту (1)"));
            return Observable.just(document);
        }).map(document -> DataUtils.parseDocument(document, group)).flatMap(tableWrapper -> {
            if (tableWrapper.getDayList() == null || tableWrapper.getDayList().size() == 0)
                return Observable.error(new UncriticalException("Timetable null or empty (2)"));
            return Observable.just(tableWrapper);
        }).map(tableWrapper -> {
            mTimetableRepository.putTimeTable(tableWrapper, group);
            return tableWrapper;
        });
    }

    @Override
    public Observable<TimeTable> getTimetableFromOnlineAssociativity(boolean online, Set<String> groups) {
        final String[] currentUrl = {"", ""};
        Map<String, Document> documentAssociation = new HashMap<>();

        return Observable.from(groups)
                .map(group -> {
                    currentUrl[1] = group;
                    return group;
                })
                .map(DataUtils::getGroupUrl)
                .map(url -> {
                    if (documentAssociation.containsKey(url))
                        return "";

                    currentUrl[0] = url;
                    return url;
                })
                .flatMap(url -> !url.equals("") ? connectAndGetData(currentUrl[1]) : Observable.just(null))
                .map(document -> document != null ? documentAssociation.put(currentUrl[0], document) : Observable.just(null))
                .toList()
                .flatMap(list -> Observable.from(groups))
                .map(group -> {
                    String url = DataUtils.getGroupUrl(group);
                    Document document = documentAssociation.get(url);

                    return DataUtils.parseDocument(document, group);
                });
    }

    @Override
    public Observable<TimeTable> getTimetableFromOffline(String group) {
        return Observable.just(
                mTimetableRepository.getTimeTable(group)
        );
    }

    @Override
    public Observable<Boolean> saveTimetable(TimeTable tableTable, String group) {
        return Observable.just(
                mTimetableRepository.putTimeTable(tableTable, group)
        );
    }

    @Override
    public Observable<Boolean> update(String minute, String group) {
        return null;
    }

    @Override
    public Observable<TimeTable> getTeacherTimeTable(boolean online, String teacherFio, Set<String> teacherGroup) {
        TimeTable teacherTimeTable = DataUtils.getEmptyWeekTimeTable()
                .setGroup(teacherFio);

        return getTimetableFromOnlineAssociativity(online, teacherGroup)
                .map(timeTable -> {
                    List<Day> days = timeTable.getDayList();
                    for (int i = 0; i < days.size(); i++){
                        Day day = days.get(i);
                        List<Lesson> lessons = day.getDayLessons();

                        if (teacherTimeTable.getDayList().get(i).getDate().equals("")) {
                            teacherTimeTable.getDayList().get(i).setDate(day.getDate()).setId(day.getId());
                        }

                        for (int i1 = 0; i1 < lessons.size(); i1++){
                            if (lessons.get(i1).getTeacher().contains(teacherFio)){
                                teacherTimeTable.getDayList().get(i).getDayLessons()
                                        .set(i1,
                                                new Lesson().setNumber(i1)
                                                        .setName(timeTable.getGroup() + "\n" + lessons.get(i1).getDoubleName())
                                                        .setTeacher(teacherFio));
                            }
                        }
                    }
                    return DataUtils.trimTimetable(teacherTimeTable);
                })
                .toList()
                .map(list -> {
                    mTimetableRepository.putTimeTable(teacherTimeTable, teacherFio);
                    return teacherTimeTable;
                });
    }
}
