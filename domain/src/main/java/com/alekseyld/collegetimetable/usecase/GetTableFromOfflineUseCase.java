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

public class GetTableFromOfflineUseCase extends UseCase<TableService> {

    private String mGroup = "";

    @Inject
    public GetTableFromOfflineUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                                      TableService tableService) {
        super(threadExecutor, postExecutionThread, tableService);
    }

    @Override
    protected Observable<TimeTable> buildUseCaseObservable() {
        return mService.getTimetableFromOffline(mGroup);
    }

    public void setGroup(String Group) {
        this.mGroup = Group;
    }
}