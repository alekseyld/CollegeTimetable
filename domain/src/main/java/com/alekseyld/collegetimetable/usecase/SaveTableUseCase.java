package com.alekseyld.collegetimetable.usecase;

import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;
import com.alekseyld.collegetimetable.service.TableService;
import com.alekseyld.collegetimetable.usecase.base.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Alekseyld on 04.11.2016.
 */

public class SaveTableUseCase extends UseCase<TableService> {

    private TimeTable mTable;
    private String mGroup;

    @Inject
    public SaveTableUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                            TableService tableService) {
        super(threadExecutor, postExecutionThread, tableService);
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable() {
        return mService.saveTimetable(mTable, mGroup);
    }

    public void setTimeTable(TimeTable table) {
        mTable = table;
    }

    public void setGroup(String group) {
        mGroup = group;
    }
}
