package com.alekseyld.collegetimetable.usecase;

import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;
import com.alekseyld.collegetimetable.service.TableService;
import com.alekseyld.collegetimetable.usecase.base.UseCase;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Alekseyld on 02.09.2016.
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
        String teacherFio = "\u0415\u0440\u043c\u043e\u043b\u0430\u0435\u0432\u0430 \u041e.\u0412.";
        Set<String> teacherGroup = new HashSet<>();
        teacherGroup.add("2 \u042d\u041d\u041d-1");
        teacherGroup.add("2 \u042d\u041d\u041d-2");
        teacherGroup.add("2 \u042d\u041d\u041d-3");
        teacherGroup.add("2 \u0422\u041e-1");
        teacherGroup.add("2 \u0422\u041e-2");

        return mService.getTeacherTimeTable(true, teacherFio, teacherGroup);

        //return mService.getTimetableFromOnline(isOnline, mGroup);
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void setGroup(String Group) {
        this.mGroup = Group;
    }
}