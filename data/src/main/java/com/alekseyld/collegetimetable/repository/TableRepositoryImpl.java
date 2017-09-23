package com.alekseyld.collegetimetable.repository;

import android.content.SharedPreferences;

import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.repository.base.TableRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.nodes.Document;

import java.lang.reflect.Type;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TableRepositoryImpl implements TableRepository {

    private Gson mGson;
    private Type mTimeTableType;
    private SharedPreferences mPref;

    @Inject
    TableRepositoryImpl(SharedPreferences sharedPreferences){
        mPref = sharedPreferences;

        mGson = new Gson();
        mTimeTableType = new TypeToken<TimeTable>(){}.getType();
    }

    @Override
    public TimeTable getTimeTable(String group) {
        String json = mPref.getString(TIMETABLE_NEW_KEY + "_"+ group, "");
        return mGson.fromJson(json, mTimeTableType);
    }

    @Override
    public String getDocument() {
        return mPref.getString(DOC_KEY, null);
    }

    @Override
    public boolean putTimeTable(TimeTable timeTable, String group) {
        String json = mGson.toJson(timeTable);

        SharedPreferences.Editor ed = mPref.edit();

        ed.putString(TIMETABLE_NEW_KEY + "_" + group, json);

        ed.apply();
        return true;
    }

    @Override
    public void putDocument(Document document) {
        SharedPreferences.Editor ed = mPref.edit();
        ed.putString(DOC_KEY, document.text());
        ed.apply();
    }

    @Override
    public void put(TimeTable timeTable, Document document, String group) {
        putTimeTable(timeTable, group);
        putDocument(document);
    }

    //// TODO: 21.09.2017 перенести в day entity
    private String firstUpperCase(String word){
        if(word == null || word.isEmpty()) return "";//или return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
