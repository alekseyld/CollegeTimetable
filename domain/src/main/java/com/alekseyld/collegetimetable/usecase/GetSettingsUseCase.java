package com.alekseyld.collegetimetable.usecase;

import com.alekseyld.collegetimetable.SettingsWrapper;
import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;
import com.alekseyld.collegetimetable.service.SettingsService;
import com.alekseyld.collegetimetable.usecase.base.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Alekseyld on 04.11.2016.
 */

public class GetSettingsUseCase extends UseCase<SettingsService> {

    @Inject
    public GetSettingsUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SettingsService settingsService) {
        super(threadExecutor, postExecutionThread, settingsService);
    }

    @Override
    protected Observable<SettingsWrapper> buildUseCaseObservable() {
        return mService.getSettings();
    }

}
