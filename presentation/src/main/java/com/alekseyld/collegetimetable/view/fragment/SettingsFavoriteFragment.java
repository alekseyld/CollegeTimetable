package com.alekseyld.collegetimetable.view.fragment;

import com.alekseyld.collegetimetable.internal.di.component.SettingsFavoriteComponent;
import com.alekseyld.collegetimetable.presenter.SettingsFavoritePresenter;
import com.alekseyld.collegetimetable.view.SettingsFavoriteView;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;

/**
 * Created by Alekseyld on 07.09.2017.
 */

public class SettingsFavoriteFragment extends BaseFragment<SettingsFavoritePresenter> implements SettingsFavoriteView {

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    protected void initializeInjections() {
        getComponent(SettingsFavoriteComponent.class).inject(this);
    }
}
