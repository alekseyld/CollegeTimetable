package com.alekseyld.collegetimetable.presenter;

import com.alekseyld.collegetimetable.entity.Notification;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.rx.subscriber.BaseSubscriber;
import com.alekseyld.collegetimetable.usecase.GetLocalNotificationsUseCase;
import com.alekseyld.collegetimetable.usecase.GetNewNotificationsUseCase;
import com.alekseyld.collegetimetable.view.NotificationsView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 04.01.2018.
 */

public class NotificationPresenter extends BasePresenter<NotificationsView> {

    private GetLocalNotificationsUseCase mGetLocalNotificationsUseCase;
    private GetNewNotificationsUseCase mGetNewNotificationsUseCase;

    @Inject
    NotificationPresenter(GetLocalNotificationsUseCase getLocalNotificationsUseCase,
                          GetNewNotificationsUseCase getNewNotificationsUseCase) {

        mGetLocalNotificationsUseCase = getLocalNotificationsUseCase;
        mGetNewNotificationsUseCase = getNewNotificationsUseCase;
    }

    public void getNotificationOffline() {
        mView.showLoading();
        mGetLocalNotificationsUseCase.execute(new BaseSubscriber<List<Notification>>() {
            @Override
            public void onNext(List<Notification> notifications) {
                super.onNext(notifications);

                mView.setNotification(notifications);
                mView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);

                mView.hideLoading();
            }
        });
    }

    public void getNotifications() {
        mView.showLoading();
        mGetNewNotificationsUseCase.execute(new BaseSubscriber<List<Notification>>() {
            @Override
            public void onNext(List<Notification> notifications) {
                super.onNext(notifications);

//                mView.addNotification(notifications);
                mView.setNotification(notifications);
                mView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);

                mView.showError(e.getMessage());
                mView.hideLoading();
            }
        });
    }

}
