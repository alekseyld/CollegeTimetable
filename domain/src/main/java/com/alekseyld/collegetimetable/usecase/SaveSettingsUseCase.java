package com.alekseyld.collegetimetable.usecase;

import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;
import com.alekseyld.collegetimetable.service.SettingsService;
import com.alekseyld.collegetimetable.usecase.base.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Alekseyld on 03.10.2017.
 */

public class SaveSettingsUseCase extends UseCase<SettingsService>{

    private Settings mSettings;

    @Inject
    public SaveSettingsUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SettingsService settingsService) {
        super(threadExecutor, postExecutionThread, settingsService);
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable() {
        return mService.saveSettings(mSettings);
    }

    public void setSettings(Settings settings) {
        mSettings = settings;
    }
}
