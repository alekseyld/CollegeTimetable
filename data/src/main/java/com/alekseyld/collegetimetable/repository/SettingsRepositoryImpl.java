package com.alekseyld.collegetimetable.repository;

import android.app.Activity;
import android.content.SharedPreferences;

import com.alekseyld.collegetimetable.SettingsWrapper;
import com.alekseyld.collegetimetable.repository.base.BaseSettingsRepository;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.DOC_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.TIMETABLE_KEY;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class SettingsRepositoryImpl extends BaseSettingsRepository {

    private Activity mActivity;

    @Inject
    SettingsRepositoryImpl(Activity activity){
        super(activity.getSharedPreferences(NAME_FILE, MODE_PRIVATE));
        mActivity = activity;
    }
}
