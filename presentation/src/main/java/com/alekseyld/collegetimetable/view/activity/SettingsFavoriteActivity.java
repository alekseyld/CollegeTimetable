package com.alekseyld.collegetimetable.view.activity;

import com.alekseyld.collegetimetable.internal.di.component.DaggerMainComponent;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.internal.di.module.MainModule;
import com.alekseyld.collegetimetable.view.activity.base.BaseActivity;

/**
 * Created by Alekseyld on 07.09.2017.
 */

public class SettingsFavoriteActivity extends BaseActivity {

    @Override
    protected MainComponent initializeInjections() {
        return DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainModule(new MainModule(this))
                .build();
    }
}
