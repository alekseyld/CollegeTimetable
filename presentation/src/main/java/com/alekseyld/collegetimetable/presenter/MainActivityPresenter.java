package com.alekseyld.collegetimetable.presenter;

import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.entity.User;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.rx.subscriber.BaseSubscriber;
import com.alekseyld.collegetimetable.usecase.DeleteUserUseCase;
import com.alekseyld.collegetimetable.usecase.GetSettingsUseCase;
import com.alekseyld.collegetimetable.usecase.GetUserUseCase;
import com.alekseyld.collegetimetable.view.MainActivityView;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 04.01.2018.
 */

public class MainActivityPresenter extends BasePresenter<MainActivityView> {

    private DeleteUserUseCase mDeleteUserUseCase;
    private GetSettingsUseCase mGetSettingsUseCase;
    private GetUserUseCase mGetUserUseCase;

    @Inject
    MainActivityPresenter(DeleteUserUseCase deleteUserUseCase,
                          GetSettingsUseCase getSettingsUseCase,
                          GetUserUseCase getUserUseCase) {
        mDeleteUserUseCase = deleteUserUseCase;
        mGetSettingsUseCase = getSettingsUseCase;
        mGetUserUseCase = getUserUseCase;
    }

    public void prepareExit() {
        mDeleteUserUseCase.execute(new BaseSubscriber<Boolean>(){
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);

                mView.startLoginActivity();
            }
        });
    }

    public void getAndSetFavorites() {
        mGetSettingsUseCase.execute(new BaseSubscriber<Settings>() {
            @Override
            public void onNext(Settings settings) {
                super.onNext(settings);

                if (settings != null && settings.getFavoriteGroups() != null)
                    mView.buildMenu(settings.getFavoriteGroups().toArray(new String[0]));
            }
        });
    }

    public void getAndSetUserInfo() {
        mGetUserUseCase.execute(new BaseSubscriber<User>() {
            @Override
            public void onNext(User user) {
                super.onNext(user);

                if (user != null && user.getName() != null)
                    mView.setUserInfo(user);
            }
        });
    }
}
