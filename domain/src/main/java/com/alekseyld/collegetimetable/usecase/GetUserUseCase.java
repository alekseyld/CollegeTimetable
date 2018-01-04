package com.alekseyld.collegetimetable.usecase;

import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;
import com.alekseyld.collegetimetable.service.ServerService;
import com.alekseyld.collegetimetable.usecase.base.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Alekseyld on 04.01.2018.
 */

public class GetUserUseCase extends UseCase<ServerService> {

    @Inject
    public GetUserUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, ServerService serverService) {
        super(threadExecutor, postExecutionThread, serverService);
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return mService.getUser();
    }
}