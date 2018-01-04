package com.alekseyld.collegetimetable.internal.di.component;

import com.alekseyld.collegetimetable.internal.di.PerActivity;
import com.alekseyld.collegetimetable.internal.di.module.MainModule;
import com.alekseyld.collegetimetable.view.fragment.LoginFragment;
import com.alekseyld.collegetimetable.view.fragment.SettingsFragment;

import dagger.Component;

/**
 * Created by Alekseyld on 02.01.2018.
 */

@PerActivity
@Component(dependencies = {ApplicationComponent.class}, modules = {MainModule.class})
public interface LoginComponent {
    void inject(LoginFragment loginFragment);
    void inject(SettingsFragment settingsFragment);
}
