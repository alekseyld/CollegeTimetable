package com.alekseyld.collegetimetable.usecase;

import com.alekseyld.collegetimetable.TableWrapper;
import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;
import com.alekseyld.collegetimetable.service.TableService;
import com.alekseyld.collegetimetable.usecase.base.UseCase;

import java.util.HashMap;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class GetTableUseCase extends UseCase {

    TableService mService;

    @Inject
    public GetTableUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                           TableService tableService) {
        super(threadExecutor, postExecutionThread);

        mService = tableService;
    }

    @Override
    protected Observable<TableWrapper> buildUseCaseObservable() {
        return mService.getTimetable();
    }
}