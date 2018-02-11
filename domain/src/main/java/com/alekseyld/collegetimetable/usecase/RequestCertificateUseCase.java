package com.alekseyld.collegetimetable.usecase;

import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;
import com.alekseyld.collegetimetable.service.ServerService;
import com.alekseyld.collegetimetable.usecase.base.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Alekseyld on 11.02.2018.
 */

public class RequestCertificateUseCase extends UseCase<ServerService> {

    private int type;
    private int count;
    private String fio;
    private String group;
    private String studentid;
    private String district;

    @Inject
    public RequestCertificateUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, ServerService serverService) {
        super(threadExecutor, postExecutionThread, serverService);
    }

    @Override
    protected Observable<Integer> buildUseCaseObservable() {
        return mService.certificate(type, count , fio, group, studentid, district);
    }

    public void setArguments(int type, int count, String  fio, String  group, String  studentid, String  district) {
        this.type = type;
        this.count = count;
        this.fio = fio;
        this.group = group;
        this.studentid = studentid;
        this.district = district;

    }

}
