package com.alekseyld.collegetimetable.presenter;

import android.webkit.URLUtil;

import androidx.annotation.Nullable;

import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.rx.subscriber.BaseSubscriber;
import com.alekseyld.collegetimetable.usecase.GetSettingsUseCase;
import com.alekseyld.collegetimetable.usecase.SaveSettingsUseCase;
import com.alekseyld.collegetimetable.usecase.UpdateSettingsUseCase;
import com.alekseyld.collegetimetable.view.SettingsView;
import com.alekseyld.collegetimetable.view.activity.MainActivity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;

import javax.inject.Inject;


/**
 * Created by Alekseyld on 04.09.2016.
 */

public class SettingsPresenter extends BasePresenter<SettingsView> {

    private final SaveSettingsUseCase mSaveSettingsUseCase;
    private final GetSettingsUseCase mGetSettingsUseCase;
    private final UpdateSettingsUseCase mUpdateSettingsUseCase;

    private Settings mSettings;

    @Inject
    public SettingsPresenter(SaveSettingsUseCase saveSettingsUseCase,
                             GetSettingsUseCase getSettingsUseCase,
                             UpdateSettingsUseCase updateSettingsUseCase) {
        mSaveSettingsUseCase = saveSettingsUseCase;
        mGetSettingsUseCase = getSettingsUseCase;
        mUpdateSettingsUseCase = updateSettingsUseCase;

        mSettings = new Settings(new HashSet<>(), "", false, true);
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

    public void saveNotification(@Nullable String group) {
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
        processNotification(notifOn);
    }

    private void processNotification(boolean notifOn) {
        if (notifOn) {
            //TODO Request runtime notification permission
            //TODO Run Worker for update timetable
        }
    }

    private void saveSettings() {
        mSaveSettingsUseCase.setSettings(mSettings);
        mSaveSettingsUseCase.execute(new BaseSubscriber<Boolean>() {
            @Override
            public void onCompleted() {
                ((MainActivity) mView.getBaseActivity()).rebuildMenu();
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

    public void saveTeacherMode(boolean teacherMode) {
        mSettings.setTeacherMode(teacherMode);
        mSettings.setNotificationGroup("");
        saveSettings();
    }

    public boolean getChangeMode() {
        return mSettings.getChangeMode();
    }

    public boolean getTeacherMode() {
        return mSettings.getTeacherMode();
    }

    public boolean getDarkMode() {
        return mSettings.isDarkMode();
    }

    public void saveDarkMode(boolean darkMode) {
        mSettings.setDarkMode(darkMode);
        saveSettings();
    }

    public void updateLinks() {
        mUpdateSettingsUseCase.execute(new BaseSubscriber<Settings>() {

            @Override
            public void onNext(Settings settings) {
                super.onNext(settings);

                String newRootUrl = settings.getRootUrl();

                if (URLUtil.isValidUrl(newRootUrl)) {
                    try {
                        String domain = new URL(newRootUrl).getAuthority();
                        mView.showError(String.format("Новый домен: %s", domain));

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                } else {
                    mView.showError("Не удалось обновить настройки приложения");
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                e.printStackTrace();
                mView.showError("Не удалось обновить настройки приложения");
            }
        });
    }

}
