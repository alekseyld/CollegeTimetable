package com.alekseyld.collegetimetable.internal.di.component;

import com.alekseyld.collegetimetable.internal.di.PerActivity;
import com.alekseyld.collegetimetable.internal.di.module.MainModule;
import com.alekseyld.collegetimetable.view.fragment.SettingsFavoriteFragment;

import dagger.Component;

/**
 * Created by Alekseyld on 07.09.2017.
 */

@PerActivity
@Component(dependencies = {ApplicationComponent.class}, modules = {MainModule.class})
public interface SettingsFavoriteComponent {
    void inject(SettingsFavoriteFragment fragment);
}
