package com.alekseyld.collegetimetable.usecase;

import com.alekseyld.collegetimetable.entity.Notification;
import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;
import com.alekseyld.collegetimetable.service.ServerService;
import com.alekseyld.collegetimetable.usecase.base.UseCase;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Alekseyld on 05.01.2018.
 */

public class GetLocalNotificationsUseCase extends UseCase<ServerService> {

    @Inject
    public GetLocalNotificationsUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, ServerService serverService) {
        super(threadExecutor, postExecutionThread, serverService);
    }

    @Override
    protected Observable<List<Notification>> buildUseCaseObservable() {
        return mService.getLocalNotifications();
    }
}
