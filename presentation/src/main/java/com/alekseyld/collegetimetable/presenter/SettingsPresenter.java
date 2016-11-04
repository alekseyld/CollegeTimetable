package com.alekseyld.collegetimetable.presenter;

import android.text.Editable;

import com.alekseyld.collegetimetable.SettingsWrapper;
import com.alekseyld.collegetimetable.navigator.base.SettingsResultProcessor;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.subscriber.BaseSubscriber;
import com.alekseyld.collegetimetable.usecase.GetSettingsUseCase;
import com.alekseyld.collegetimetable.usecase.SaveSettingsUseCase;
import com.alekseyld.collegetimetable.view.SettingsView;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class SettingsPresenter extends BasePresenter<SettingsView> {

    private SettingsResultProcessor mProcessor;

    private SettingsWrapper mSettings;
    private SaveSettingsUseCase mSaveSettingsUseCase;
    private GetSettingsUseCase mGetSettingsUseCase;

    @Inject
    public SettingsPresenter(SettingsResultProcessor settingsResultProcessor,
                             SaveSettingsUseCase saveSettingsUseCase,
                             GetSettingsUseCase getSettingsUseCase) {
        mProcessor = settingsResultProcessor;
        mSaveSettingsUseCase = saveSettingsUseCase;
        mGetSettingsUseCase = getSettingsUseCase;
        mSettings = new SettingsWrapper(new HashSet<String>(), "", true);
    }

    @Override
    public void resume() {
        mGetSettingsUseCase.execute(new BaseSubscriber<SettingsWrapper>(){
            @Override
            public void onNext(SettingsWrapper settings) {
                mSettings = settings;
            }
        });
    }

    public SettingsWrapper getSettings() {
        return mSettings;
    }

    @Deprecated
    public void updateSettings(Editable minute, Editable group){
        /*mPref = mView.context().getSharedPreferences(NAME_FILE, MODE_PRIVATE);
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
        mProcessor.processSettingsResult(mView.getAct());*/
    }

    public void saveFavorite(Set<String> groups){
        if(groups != null && groups.size() >= 0){
            mSettings.setFavoriteGroups(groups);
            mSaveSettingsUseCase.setSettings(mSettings);
            mSaveSettingsUseCase.execute(new BaseSubscriber<Boolean>(){
                @Override
                public void onCompleted() {
                    mView.getAct().rebuildMenu();
                }
            });
        }
    }

    public void saveNotification(Editable group){
        if(group != null && !group.toString().equals("")){
            mSettings.setNotificationGroup(group.toString());
        }
    }

    public void saveAlarmMode(boolean alarmMode){
        mSettings.setAlarmMode(alarmMode);
    }

    @Override
    public void destroy() {
        mSaveSettingsUseCase.setSettings(mSettings);
        mSaveSettingsUseCase.execute(new BaseSubscriber<Boolean>());
    }
}
