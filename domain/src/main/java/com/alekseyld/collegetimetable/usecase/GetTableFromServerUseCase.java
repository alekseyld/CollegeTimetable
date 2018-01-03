package com.alekseyld.collegetimetable.usecase;

import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;
import com.alekseyld.collegetimetable.service.ServerService;
import com.alekseyld.collegetimetable.usecase.base.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Alekseyld on 03.01.2018.
 */

public class GetTableFromServerUseCase extends UseCase<ServerService> {

    private String mGroup;

    @Inject
    public GetTableFromServerUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                                     ServerService serverService) {
        super(threadExecutor, postExecutionThread, serverService);
    }

    @Override
    protected Observable<TimeTable> buildUseCaseObservable() {
        return mService.getTimetableFromServer(mGroup);
    }

    public GetTableFromServerUseCase setGroup(String group) {
        this.mGroup = group;
        return this;
    }
}
