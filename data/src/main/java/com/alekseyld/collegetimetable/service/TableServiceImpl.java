package com.alekseyld.collegetimetable.service;

import android.util.Log;

import com.alekseyld.collegetimetable.api.SettingsApi;
import com.alekseyld.collegetimetable.dto.SettingsDto;
import com.alekseyld.collegetimetable.dto.TimeTableDto;
import com.alekseyld.collegetimetable.entity.Day;
import com.alekseyld.collegetimetable.entity.Lesson;
import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.exception.UncriticalException;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;
import com.alekseyld.collegetimetable.repository.base.TableRepository;
import com.alekseyld.collegetimetable.utils.DataUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import rx.Emitter;
import rx.Observable;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TableServiceImpl implements TableService {

    private final String TAG = "TableServiceImpl";

    private final TableRepository mTimetableRepository;
    private final SettingsRepository mSettingsRepository;
    private final SettingsApi mSettingsApi;
    private final GroupService mGroupService;
    private Settings mSettings;

    private boolean hasError = false;

    @Inject
    public TableServiceImpl(TableRepository tableRepository,
                            SettingsRepository settingsRepository,
                            SettingsApi settingsApi,
                            GroupService groupService
    ) {
        mSettingsRepository = settingsRepository;
        mTimetableRepository = tableRepository;
        mGroupService = groupService;
        this.mSettingsApi = settingsApi;
    }

    private Observable<String> updateSettingsOnlineAndGetUrl(String group) {
        return updateSettingsOnline()
            .map(settings -> mGroupService.getGroupUrl(settings.getRootUrl(), group, settings.getAbbreviationMap(), settings.getNeftGroup()));
    }

    private Observable<Settings> updateSettingsOnline() {
        return mSettingsApi.getSettings()
            .map(mSettingsRepository::updateSettings)
            .map(SettingsDto::toEntity);
    }

    private Observable<Settings> getSettings() {
        return Observable.just(mSettingsRepository.getSettings().toEntity());
    }

    private Observable<String> getGroupUrl(final String group) {
        return getSettings()
            .map(settings -> {
                mSettings = settings;
                return mGroupService.getGroupUrl(settings.getRootUrl(), group, settings.getAbbreviationMap(), mSettings.getNeftGroup());
            })
            .flatMap(url -> {
                if (!url.isEmpty() && !hasError) return Observable.just(url);
                return Observable.error(new UncriticalException("empty"));
            })
            .onErrorResumeNext(throwable -> updateSettingsOnlineAndGetUrl(group));
    }

    private Observable<Document> connectAndGetData(String group) {
        return getGroupUrl(group)
            .flatMap(url -> {
                Document document;

                try {
                    document = Jsoup.connect(url).get();
                } catch (IOException e) {
                    Log.d(TAG, "", e);
                    document = null;
                } catch (IllegalArgumentException e1) {
                    document = null;
                }

                return Observable.just(document);
            }).flatMap(document -> {
                if (isTableHtml(document)) {
                    return Observable.just(new java.util.HashMap.SimpleEntry<String, Document>(null, document));
                }

                return updateSettingsOnlineAndGetUrl(group)
                    .map(url -> new HashMap.SimpleEntry<String, Document>(url, null));

            }).flatMap(entry -> {
                if (entry.getValue() != null) {
                    return Observable.just(entry.getValue());
                }

                Document document = null;

                boolean hasExternalSettings = mSettings != null && mSettings.hasExternalSettings();
                for (int i = 0; i < 5; i++) {
                    try {
                        if (hasExternalSettings) {
                            String groupUrl = mGroupService.getGroupUrl(mSettings.getRootUrl(), group, mSettings.getAbbreviationMap(), mSettings.getNeftGroup());

                            document = Jsoup.connect(groupUrl).timeout(5000).get();
                        } else {
                            document = Jsoup.connect(mGroupService.getGroupUrl(group)).timeout(5000).get();
                        }
                    } catch (IOException | IllegalArgumentException e) {
                        Log.d(TAG, "", e);
                    }

                    if (document != null) {
                        break;
                    }
                }

                return Observable.just(document);
            }).flatMap(document -> {
                if (isTableHtml(document)) {
                    return Observable.just(document);
                }

                try {
                    if (mSettings != null && mSettings.hasExternalSettings()) {
                        document = Jsoup.connect(
                                mGroupService.getGroupUrl(mSettings.getRootUrl(), group, mSettings.getAbbreviationMap(), mSettings.getNeftGroup()))
                            .timeout(5000).get();
                    }
                } catch (IOException e) {
                    Log.d(TAG, "", e);
                    document = null;
                } catch (IllegalArgumentException e1) {
                    return Observable.error(new UncriticalException("Некорректно введена группа"));
                }

                return Observable.just(document);
            }).flatMap(document -> {
                if (document == null || !document.html().contains("<body bgcolor=\"bbbbbb\">")) {
                    return Observable.error(new UncriticalException("Не удалось подключиться к сайту (0:1)"));
                }

                return Observable.just(document);
            });
    }

    private boolean isTableHtml(Document document) {
        return document != null && document.html().contains("<body bgcolor=\"bbbbbb\">");
    }

    @Override
    public Observable<TimeTable> getTimetableFromOnline(boolean online, String group) {
        return connectAndGetData(group).flatMap(document -> {
            if (document == null)
                return Observable.error(new UncriticalException("Не удалось подключиться к сайту (1)"));
            return Observable.just(document);
        }).map(document -> DataUtils.parseDocument(document, group)).flatMap(tableWrapper -> {
            if (tableWrapper.getDayList() == null || tableWrapper.getDayList().isEmpty()) {
                if (!hasError) {
                    hasError = true;
                    return getTimetableFromOnline(online, group);
                }
                return Observable.error(new UncriticalException("Ну удалось получить расписание (2)"));
            }

            return Observable.just(tableWrapper);
        }).map(tableWrapper -> {
            mTimetableRepository.putTimeTable(new TimeTableDto(tableWrapper), group);

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
            .flatMap(this::getGroupUrl)
            .map(url -> {
                if (documentAssociation.containsKey(url))
                    return "";

                currentUrl[0] = url;
                return url;
            })
            .flatMap(url -> !url.isEmpty() ? connectAndGetData(currentUrl[1]) : Observable.just(null))
            .map(document -> document != null ? documentAssociation.put(currentUrl[1], document) : Observable.just(null))
            .toList()
            .flatMap(list -> Observable.from(groups))
            .map(group -> {
                Document document = documentAssociation.get(group);

                return DataUtils.parseDocument(document, group);
            });
    }

    @Override
    public Observable<TimeTable> getTimetableFromOffline(String group) {
        return Observable.create(timeTableEmitter -> {
            TimeTableDto dto = mTimetableRepository.getTimeTable(group);

            if (dto != null) {
                timeTableEmitter.onNext(dto.toEntity());
            } else {
                timeTableEmitter.onNext(null);
            }
            timeTableEmitter.onCompleted();
        }, Emitter.BackpressureMode.BUFFER);
    }

    @Override
    public Observable<Boolean> saveTimetable(TimeTable tableTable, String group) {
        return Observable.just(
            mTimetableRepository.putTimeTable(new TimeTableDto(tableTable), group)
        );
    }

    @Override
    public Observable<Boolean> update(String minute, String group) {
        return null;
    }

    @Override
    public Observable<TimeTable> getTeacherTimeTable(boolean online, String teacherFio, Set<String> teacherGroup) {
        TimeTable teacherTimeTable = DataUtils.getEmptyWeekTimeTable(7, 8, true)
            .setGroup(teacherFio);

        return getTimetableFromOnlineAssociativity(online, teacherGroup)
            .map(timeTable -> {
                List<Day> days = timeTable.getDayList();
                for (int i = 0; i < days.size(); i++) {
                    Day day = days.get(i);
                    List<Lesson> lessons = day.getDayLessons();

                    if (i == teacherTimeTable.getDayList().size()) {
                        teacherTimeTable.getDayList().add(DataUtils.getEmptyDay(i, 7));
                    }

                    if (teacherTimeTable.getDayList().get(i).getDate().isEmpty()) {
                        teacherTimeTable.getDayList().get(i).setDate(day.getDate()).setId(day.getId());
                    }

                    for (int i1 = 0; i1 < lessons.size(); i1++) {
                        if (lessons.get(i1).getTeacher().contains(teacherFio)) {
                            if (teacherTimeTable.getDayList().get(i).getDayLessons().get(i1).getDoubleName().isEmpty()) {
                                teacherTimeTable.getDayList().get(i).getDayLessons()
                                    .set(i1, new Lesson()
                                        .setNumber(i1)
                                        .setName(timeTable.getGroup() + "\n" + lessons.get(i1).getDoubleName())
                                        .setTime(lessons.get(i1).getTime())
                                        .setTeacher(teacherFio)
                                    );
                            } else {
                                teacherTimeTable.getDayList().get(i).getDayLessons()
                                    .set(i1, new Lesson().setNumber(i1)
                                        .setName(
                                            teacherTimeTable.getDayList().get(i).getDayLessons().get(i1).getDoubleName()
                                                + "\n\n" +
                                                timeTable.getGroup() + "\n" + lessons.get(i1).getDoubleName())
                                        .setTeacher(teacherFio));
                            }
                        }
                    }
                }
                return teacherTimeTable;
            })
            .toList()
            .map(list -> {
                mTimetableRepository.putTimeTable(new TimeTableDto(teacherTimeTable), teacherFio);
                return teacherTimeTable;
            });
    }
}
