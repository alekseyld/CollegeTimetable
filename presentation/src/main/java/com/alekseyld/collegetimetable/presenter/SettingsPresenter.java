package com.alekseyld.collegetimetable.presenter;

import android.content.SharedPreferences;
import android.text.Editable;

import com.alekseyld.collegetimetable.navigator.base.SettingsResultProcessor;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.view.SettingsView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;
import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.FAVORITEGROUPS_KEY;
import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.GROUP_KEY;
import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.TIME_KEY;
import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.URL_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.DOC_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.TIMETABLE_KEY;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class SettingsPresenter extends BasePresenter<SettingsView> {

    private SharedPreferences mPref;
    private SettingsResultProcessor mProcessor;

    @Inject
    public SettingsPresenter(SettingsResultProcessor settingsResultProcessor) {
        mProcessor = settingsResultProcessor;
    }

    public void updateSettings(Editable minute, Editable group){
        mPref = mView.context().getSharedPreferences(NAME_FILE, MODE_PRIVATE);
        if(minute != null && !minute.toString().equals("")){
            SharedPreferences.Editor ed = mPref.edit();
            ed.putInt(TIME_KEY, Integer.parseInt(minute.toString()));
            ed.apply();
        }
        if(group != null && !group.toString().equals("")){
            SharedPreferences.Editor ed = mPref.edit();
            ed.putString(GROUP_KEY, group.toString());
            ed.remove(DOC_KEY);
            ed.remove(TIMETABLE_KEY);
            ed.remove(URL_KEY);
            ed.apply();
        }
        mView.showError("Сохранено");
        mProcessor.processSettingsResult(mView.getAct());
    }

    public void saveFavorite(Set<String> groups){
        mPref = mView.context().getSharedPreferences(NAME_FILE, MODE_PRIVATE);

        if(groups != null && groups.size() > 0){
            SharedPreferences.Editor ed = mPref.edit();
            ed.remove(FAVORITEGROUPS_KEY);
            ed.putStringSet(FAVORITEGROUPS_KEY, groups);
            ed.apply();
            mView.showError("Сохранено");
            mView.getAct().rebuildMenu();
        }
    }

    public void saveNotification(Editable group){
        mPref = mView.context().getSharedPreferences(NAME_FILE, MODE_PRIVATE);
        if(group != null && !group.toString().equals("")){
            SharedPreferences.Editor ed = mPref.edit();
            ed.putString(GROUP_KEY, group.toString().toUpperCase());
            ed.remove(DOC_KEY);
            ed.remove(TIMETABLE_KEY);
            ed.remove(URL_KEY);
            ed.apply();
            mView.showError("Сохранено");
        }
    }

}
