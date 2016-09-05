package com.alekseyld.collegetimetable.presenter;

import android.content.SharedPreferences;
import android.text.Editable;

import com.alekseyld.collegetimetable.navigator.base.SettingsResultProcessor;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.view.SettingsView;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

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
        mPref = mView.context().getSharedPreferences("DataStorage", MODE_PRIVATE);
        if(minute != null && !minute.toString().equals("")){
            SharedPreferences.Editor ed = mPref.edit();
            ed.putInt("Time", Integer.parseInt(minute.toString()));
            ed.apply();
        }
        if(group != null && !group.toString().equals("")){
            SharedPreferences.Editor ed = mPref.edit();
            ed.putString("Group", group.toString());
            ed.remove("Doc");
            ed.remove("TimeTable");
            ed.apply();
        }
        mView.showError("Сохранено");
        mProcessor.processSettingsResult(mView.getAct());
    }

}
