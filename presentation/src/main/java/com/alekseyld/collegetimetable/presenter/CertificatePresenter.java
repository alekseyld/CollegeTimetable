package com.alekseyld.collegetimetable.presenter;

import com.alekseyld.collegetimetable.entity.User;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.rx.subscriber.BaseSubscriber;
import com.alekseyld.collegetimetable.usecase.GetUserUseCase;
import com.alekseyld.collegetimetable.usecase.RequestCertificateUseCase;
import com.alekseyld.collegetimetable.view.CertificateView;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 04.01.2018.
 */

public class CertificatePresenter extends BasePresenter<CertificateView> {

    private RequestCertificateUseCase mRequestCertificateUseCase;

    private GetUserUseCase mGetUserUseCase;

    private User mUser;

    @Inject
    CertificatePresenter(GetUserUseCase getUserUseCase,
                         RequestCertificateUseCase requestCertificateUseCase) {

        mGetUserUseCase = getUserUseCase;
        mRequestCertificateUseCase = requestCertificateUseCase;
    }

    @Override
    public void resume() {
        super.resume();

        mGetUserUseCase.execute(new BaseSubscriber<User>() {

            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(User user) {
                super.onNext(user);

                mUser = user;
                mView.setUserInfo(mUser);
            }
        });
    }

    public User getUser() {
        return mUser;
    }

    public void sendCertificate(int type,
                                int count,
                                String fio,
                                String group,
                                String studentid,
                                String district) {

        mView.showLoading();

        mRequestCertificateUseCase.setArguments(type, count, fio, group, studentid, district);
        mRequestCertificateUseCase.execute(new BaseSubscriber<Integer>() {
            @Override
            public void onCompleted() {
                super.onCompleted();
                mView.hideLoading();
                mView.back();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showError("Произошла ошибка при заказе справки");
                e.printStackTrace();
                mView.hideLoading();
                mView.back();
            }

            @Override
            public void onNext(Integer integer) {
                super.onNext(integer);

                if (integer == 0) {
                    mView.showError("Справка успешно заказана");
                } else {
                    mView.showError("Произошла ошибка при заказе справки");
                }
            }
        });

    }
}
