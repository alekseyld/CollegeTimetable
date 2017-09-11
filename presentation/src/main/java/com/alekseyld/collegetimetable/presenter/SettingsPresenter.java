package com.alekseyld.collegetimetable.presenter;

import android.text.Editable;

import com.alekseyld.collegetimetable.SettingsWrapper;
import com.alekseyld.collegetimetable.navigator.base.SettingsResultProcessor;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.rx.subscriber.BaseSubscriber;
import com.alekseyld.collegetimetable.usecase.GetSettingsUseCase;
import com.alekseyld.collegetimetable.usecase.SaveSettingsUseCase;
import com.alekseyld.collegetimetable.view.SettingsView;
import com.alekseyld.collegetimetable.view.activity.MainActivity;

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
        mSettings = new SettingsWrapper(new HashSet<String>(), "", false, true);
    }

    @Override
    public void resume() {
        mGetSettingsUseCase.execute(new BaseSubscriber<SettingsWrapper>() {
            @Override
            public void onNext(SettingsWrapper settings) {
                mSettings = settings;
            }

            @Override
            public void onCompleted() {
                mView.presenterReady();
            }
        });
    }

    public boolean getAlarmMode() {
        return mSettings.getAlarmMode();
    }

    public boolean getNotifOn() {
        return mSettings.getNotifOn();
    }

    public SettingsWrapper getSettings() {
        return mSettings;
    }

    @Deprecated
    public void updateSettings(Editable minute, Editable group) {
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

    public void saveFavorite(Set<String> groups) {
        if (groups != null && groups.size() >= 0) {
            mSettings.setFavoriteGroups(groups);
            mSaveSettingsUseCase.setSettings(mSettings);
            mSaveSettingsUseCase.execute(new BaseSubscriber<Boolean>() {
                @Override
                public void onCompleted() {
                    ((MainActivity)mView.getBaseActivity()).rebuildMenu();
                }
            });
        }
    }

    public void saveNotification(String group) {
        if (group == null) {
            mView.showError("Заполните все поля группы");
            return;
        }

        mSettings.setNotificationGroup(group);
        saveSettings();
    }

    public void saveAlarmMode(boolean alarmMode) {
        mSettings.setAlarmMode(alarmMode);
        saveSettings();
    }

    public void saveNotifOn(boolean notifOn) {
        mSettings.setNotifOn(notifOn);
        saveSettings();
    }

    private void saveSettings() {
        mSaveSettingsUseCase.setSettings(mSettings);
        mSaveSettingsUseCase.execute(new BaseSubscriber<Boolean>());
    }

    @Override
    public void destroy() {
//        saveSettings();
    }
}
