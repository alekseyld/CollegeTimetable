package com.alekseyld.collegetimetable.repository;

import android.app.Activity;

import com.alekseyld.collegetimetable.repository.base.BaseSettingsRepository;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;

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
