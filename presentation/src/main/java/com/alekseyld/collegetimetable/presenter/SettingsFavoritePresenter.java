package com.alekseyld.collegetimetable.presenter;

import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.rx.subscriber.BaseSubscriber;
import com.alekseyld.collegetimetable.usecase.GetSettingsUseCase;
import com.alekseyld.collegetimetable.usecase.SaveSettingsUseCase;
import com.alekseyld.collegetimetable.view.SettingsFavoriteView;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 07.09.2017.
 */

public class SettingsFavoritePresenter extends BasePresenter<SettingsFavoriteView> {

    private Settings mSettings;
    private SaveSettingsUseCase mSaveSettingsUseCase;
    private GetSettingsUseCase mGetSettingsUseCase;

    @Inject
    public SettingsFavoritePresenter(SaveSettingsUseCase saveSettingsUseCase,
                             GetSettingsUseCase getSettingsUseCase) {
        mSaveSettingsUseCase = saveSettingsUseCase;
        mGetSettingsUseCase = getSettingsUseCase;
    }

    @Override
    public void resume() {
        mGetSettingsUseCase.execute(new BaseSubscriber<Settings>() {
            @Override
            public void onNext(Settings settings) {
                mSettings = settings;
            }

            @Override
            public void onCompleted() {
                refreshFavoriteGroups();
            }
        });
    }

    public void addFavoriteGroup(String group, boolean teacherMode) {
        if (teacherMode) {
            mSettings.addTeacherGroup(group);
        } else {
            mSettings.addFavoriteGroup(group);
        }
        mSaveSettingsUseCase.setSettings(mSettings);
        mSaveSettingsUseCase.execute(new BaseSubscriber<Boolean>() {
            @Override
            public void onCompleted() {
                refreshFavoriteGroups();
            }
        });

    }

    private void refreshFavoriteGroups() {
        mView.getAdapter().removeAll();
        if (mView.getTeacherMode()) {
            mView.getAdapter().addAll(new ArrayList<>(mSettings.getTeacherGroups()));
        } else {
            mView.getAdapter().addAll(new ArrayList<>(mSettings.getFavoriteGroups()));
        }
        mView.getAdapter().notifyDataSetChanged();

        if (mView.getAdapter().getItemCount() == 0)
            mView.setMessage("Список избранных групп пуст");
        else
            mView.hideLoading();
    }

    public void removeFavoriteGroup(String group, boolean teacherMode) {
        if (teacherMode) {
            mSettings.removeTeacherGroup(group);
        } else {
            mSettings.removeFavoriteGroup(group);
        }

        mSaveSettingsUseCase.setSettings(mSettings);
        mSaveSettingsUseCase.execute(new BaseSubscriber<Boolean>() {
            @Override
            public void onCompleted() {
                refreshFavoriteGroups();
            }
        });
    }
}
