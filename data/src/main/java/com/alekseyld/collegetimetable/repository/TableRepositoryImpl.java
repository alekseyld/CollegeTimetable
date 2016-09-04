package com.alekseyld.collegetimetable.repository;

import android.app.Activity;
import android.content.SharedPreferences;

import com.alekseyld.collegetimetable.TableWrapper;
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

    private Activity mActivity;
    private SharedPreferences mPref;

    @Inject
    TableRepositoryImpl(Activity activity){
        mActivity = activity;
        mPref = mActivity.getSharedPreferences("DataStorage", MODE_PRIVATE);

        mGson = new Gson();
        mType = new TypeToken<HashMap<TableWrapper.Day, HashMap<TableWrapper.Lesson, String>>>(){}.getType();
    }

    @Override
    public TableWrapper getTimeTable() {
        String s = mPref.getString("TimeTable", "no");
        TableWrapper tableWrapper = new TableWrapper();
        tableWrapper.setTimeTable(mGson.fromJson(s, mType));
        return tableWrapper;
    }

    @Override
    public String getDocument() {
        return mPref.getString("Doc", null);
    }

    @Override
    public void putTimeTable(TableWrapper tableWrapper) {
        String json = mGson.toJson(tableWrapper.getmTimeTable());
        SharedPreferences.Editor ed = mPref.edit();
        ed.putString("TimeTable", json);
        ed.apply();
    }

    @Override
    public void putDocument(Document document) {
        SharedPreferences.Editor ed = mPref.edit();
        ed.putString("Doc", document.text());
        ed.apply();
    }

    @Override
    public void put(TableWrapper tableWrapper, Document document) {
        putTimeTable(tableWrapper);
        putDocument(document);
    }
}
