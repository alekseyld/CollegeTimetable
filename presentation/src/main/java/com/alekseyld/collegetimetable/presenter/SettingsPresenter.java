package com.alekseyld.collegetimetable.presenter;

import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.job.TimetableJob;
import com.alekseyld.collegetimetable.navigator.base.SettingsResultProcessor;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.rx.subscriber.BaseSubscriber;
import com.alekseyld.collegetimetable.usecase.GetSettingsUseCase;
import com.alekseyld.collegetimetable.usecase.SaveSettingsUseCase;
import com.alekseyld.collegetimetable.utils.Utils;
import com.alekseyld.collegetimetable.view.SettingsView;
import com.alekseyld.collegetimetable.view.activity.MainActivity;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.util.HashSet;
import java.util.Set;

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
    public SettingsPresenter(SettingsResultProcessor settingsResultProcessor,
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
        processNotification(notifOn);
    }

    private void processNotification(boolean notifOn) {
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(TimetableJob.TAG);

        JobRequest jobRequest = null;
        if (jobRequests.size() > 1) {
            int i = 0;
            for (JobRequest j: jobRequests) {
                if (i++ == 0 && notifOn) {
                    jobRequest = j;
                } else {
                    j.cancelAndEdit();
                }
            }
        }

        if (notifOn) {
            if (jobRequest == null) {
                jobRequest = Utils.getTimeTableJob();
            }

            jobRequest.cancelAndEdit().startNow().build().schedule();
//            mView.getBaseActivity().startService(
//                    new Intent(mView.getBaseActivity(), UpdateTimetableService.class));
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
}
