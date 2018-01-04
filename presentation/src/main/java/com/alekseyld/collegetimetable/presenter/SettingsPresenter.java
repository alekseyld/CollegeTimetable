package com.alekseyld.collegetimetable.presenter;

import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.navigator.base.SettingsResultProcessor;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.rx.subscriber.BaseSubscriber;
import com.alekseyld.collegetimetable.usecase.GetSettingsUseCase;
import com.alekseyld.collegetimetable.usecase.SaveSettingsUseCase;
import com.alekseyld.collegetimetable.view.SettingsView;
import com.alekseyld.collegetimetable.view.activity.MainActivity;

import java.util.HashSet;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class SettingsPresenter extends BasePresenter<SettingsView> {

    private SettingsResultProcessor mProcessor;

    private Settings mSettings;
    private SaveSettingsUseCase mSaveSettingsUseCase;
    private GetSettingsUseCase mGetSettingsUseCase;

    @Inject
    SettingsPresenter(SettingsResultProcessor settingsResultProcessor,
                             SaveSettingsUseCase saveSettingsUseCase,
                             GetSettingsUseCase getSettingsUseCase) {
        mProcessor = settingsResultProcessor;
        mSaveSettingsUseCase = saveSettingsUseCase;
        mGetSettingsUseCase = getSettingsUseCase;
        mSettings = new Settings(new HashSet<String>(), "", false, true);
    }

    @Override
    public void resume() {
        mGetSettingsUseCase.execute(new BaseSubscriber<Settings>() {
            @Override
            public void onNext(Settings settings) {
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

    public String getNotificationGroup() {
        return mSettings.getNotificationGroup();
    }

    public Settings getSettings() {
        return mSettings;
    }

    public void saveNotification(String group) {
        if (group == null) {
            mView.showError("Заполните все поля группы");
            return;
        }

        mSettings.setNotificationGroup(group);
        saveSettings();
        mView.presenterReady();
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
        mSaveSettingsUseCase.execute(new BaseSubscriber<Boolean>(){
            @Override
            public void onCompleted() {
                ((MainActivity)mView.getBaseActivity()).rebuildMenu();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                e.printStackTrace();
            }
        });
    }

    public void saveChangeMode(boolean changeMode) {
        mSettings.setChangeMode(changeMode);
        saveSettings();
    }

    public boolean getChangeMode() {
        return mSettings.getChangeMode();
    }

    public void saveUrlServer(String urlServer) {
        mSettings.setUrlServer(urlServer);
        saveSettings();
    }
}
