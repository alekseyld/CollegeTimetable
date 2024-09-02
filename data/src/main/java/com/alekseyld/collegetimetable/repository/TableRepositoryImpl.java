package com.alekseyld.collegetimetable.repository;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.alekseyld.collegetimetable.dto.TimeTableDto;
import com.alekseyld.collegetimetable.repository.base.TableRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TableRepositoryImpl implements TableRepository {

    private final Gson mGson;
    private final Type mTimeTableType;
    private final SharedPreferences mPref;

    @Inject
    TableRepositoryImpl(SharedPreferences sharedPreferences){
        mPref = sharedPreferences;

        mGson = new Gson();
        mTimeTableType = new TypeToken<TimeTableDto>(){}.getType();
    }

    @Nullable
    @Override
    public TimeTableDto getTimeTable(String group) {
        String json = mPref.getString(TIMETABLE_NEW_KEY + "_"+ group, "");
        return mGson.fromJson(json, mTimeTableType);
    }

    @Override
    public boolean putTimeTable(TimeTableDto timeTable, String group) {
        String json = mGson.toJson(timeTable);
        SharedPreferences.Editor ed = mPref.edit();
        ed.putString(TIMETABLE_NEW_KEY + "_" + group, json);
        ed.apply();
        return true;
    }
}
