package com.alekseyld.collegetimetable.presenter;

import com.alekseyld.collegetimetable.exception.UncriticalException;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.rx.subscriber.BaseSubscriber;
import com.alekseyld.collegetimetable.usecase.AuthUseCase;
import com.alekseyld.collegetimetable.view.LoginView;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 02.01.2018.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    private AuthUseCase mAuthUseCase;

    @Inject
    LoginPresenter(AuthUseCase authUseCase) {
        mAuthUseCase = authUseCase;
    }

    public void login(String login, String password) {

        if (login.equals("")) {
            mView.showError("Логин не может быть пустым");
            return;
        } else if (password.equals("")) {
            mView.showError("Пароль не может быть пустым");
            return;
        }

        mView.showLoading();
        mAuthUseCase.setLogin(login)
                .setPassword(password)
                .execute(new BaseSubscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage().contains("Failed to connect to")) {

                            e = new UncriticalException("Не удалось подключиться к серверу " + e.getMessage().replace("Failed to connect to", ""));
                        }
                        super.onError(e);
                        e.printStackTrace();

                        mView.showError(e.getMessage());
                        mView.hideLoading();
                    }

                    @Override
                    public void onNext(Boolean isLoginSuccess) {
                        super.onNext(isLoginSuccess);

                        if (isLoginSuccess)
                            mView.loginSuccessfully();
                    }

                });
    }
}
