package com.alekseyld.collegetimetable.repository;

import android.app.Activity;
import android.content.SharedPreferences;

import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.repository.base.TableRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.nodes.Document;

import java.lang.reflect.Type;
import java.util.HashMap;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TableRepositoryImpl implements TableRepository {

    private Gson mGson;
    private Type mType;
    private Type mTypeDay;

    private Activity mActivity;
    private SharedPreferences mPref;

    @Inject
    TableRepositoryImpl(Activity activity){
        mActivity = activity;
        mPref = mActivity.getSharedPreferences(NAME_FILE, MODE_PRIVATE);

        mGson = new Gson();
        mType = new TypeToken<HashMap<TimeTable.Day, HashMap<TimeTable.Lesson, String>>>(){}.getType();
        mTypeDay = new TypeToken<HashMap<TimeTable.Day, String>>(){}.getType();
    }

    @Override
    public TimeTable getTimeTable(String group) {
        String s = mPref.getString(TIMETABLE_KEY + "_"+ group, "");
        String d = mPref.getString(DAYS_KEY + "_" + group, "");

        TimeTable timeTable = new TimeTable();
        timeTable.setTimeTable(mGson.fromJson(s, mType));
        timeTable.setDays(mGson.fromJson(d, mTypeDay));
        return timeTable;
    }

    @Override
    public String getDocument() {
        return mPref.getString(DOC_KEY, null);
    }

    @Override
    public boolean putTimeTable(TimeTable timeTable, String group) {
        String json = mGson.toJson(timeTable.getTimeTable());
        editDays(timeTable.getDays());
        String json2 = mGson.toJson(timeTable.getDays());
        SharedPreferences.Editor ed = mPref.edit();

        ed.putString(TIMETABLE_KEY + "_" + group, json);
        ed.putString(DAYS_KEY + "_" + group, json2);

        ed.apply();
        return true;
    }

    private void editDays(HashMap<TimeTable.Day, String> days) {
        for(TimeTable.Day d: days.keySet()){
            days.put(d, firstUpperCase(days.get(d).toLowerCase()));
        }
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

    private String firstUpperCase(String word){
        if(word == null || word.isEmpty()) return "";//или return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
