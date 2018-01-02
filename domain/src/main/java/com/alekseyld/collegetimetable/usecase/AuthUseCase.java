package com.alekseyld.collegetimetable.usecase;

import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;
import com.alekseyld.collegetimetable.service.ServerService;
import com.alekseyld.collegetimetable.usecase.base.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Alekseyld on 02.01.2018.
 */

public class AuthUseCase extends UseCase<ServerService> {

    private String mLogin;
    private String mPassword;

    @Inject
    public AuthUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, ServerService serverService) {
        super(threadExecutor, postExecutionThread, serverService);
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable() {
        return mService.auth(mLogin, mPassword);
    }

    public AuthUseCase setLogin(String login) {
        this.mLogin = login;
        return this;
    }

    public AuthUseCase setPassword(String password) {
        this.mPassword = password;
        return this;
    }
}
