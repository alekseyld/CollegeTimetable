package com.alekseyld.collegetimetable.usecase;

import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;
import com.alekseyld.collegetimetable.service.TableService;
import com.alekseyld.collegetimetable.usecase.base.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Alekseyld on 02.09.2017.
 */

public class GetTableFromOnlineUseCase extends UseCase<TableService> {

    private boolean isOnline = true;
    private String mGroup = "";

    @Inject
    public GetTableFromOnlineUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                                     TableService tableService) {
        super(threadExecutor, postExecutionThread, tableService);
    }

    @Override
    protected Observable<TimeTable> buildUseCaseObservable() {
        return mService.getTimetableFromOnline(isOnline, mGroup);
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void setGroup(String Group) {
        this.mGroup = Group;
    }
}