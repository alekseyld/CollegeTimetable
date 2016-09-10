package com.alekseyld.collegetimetable.usecase;

import com.alekseyld.collegetimetable.TableWrapper;
import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;
import com.alekseyld.collegetimetable.service.TableService;
import com.alekseyld.collegetimetable.usecase.base.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class GetTableUseCase extends UseCase {

    private TableService mService;
    private boolean isOnline = true;
    private String mGroup = "";

    @Inject
    public GetTableUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                           TableService tableService) {
        super(threadExecutor, postExecutionThread);

        mService = tableService;
    }

    @Override
    protected Observable<TableWrapper> buildUseCaseObservable() {
        return mService.getTimetable(isOnline, mGroup);
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void setGroup(String Group) {
        this.mGroup = Group;
    }
}