package com.alekseyld.collegetimetable.internal.di.component;

import com.alekseyld.collegetimetable.internal.di.PerActivity;
import com.alekseyld.collegetimetable.internal.di.module.MainModule;
import com.alekseyld.collegetimetable.view.fragment.AboutFragment;
import com.alekseyld.collegetimetable.view.fragment.BellTableFragment;
import com.alekseyld.collegetimetable.view.fragment.SettingsFragment;
import com.alekseyld.collegetimetable.view.fragment.TableFragment;
import com.alekseyld.collegetimetable.view.fragment.WebViewFragment;

import dagger.Component;

/**
 * Created by Alekseyld on 02.09.2016.
 */

@PerActivity
@Component(dependencies = {ApplicationComponent.class}, modules = {MainModule.class})
public interface MainComponent {
    void inject(TableFragment tableFragment);
    void inject(SettingsFragment settingsFragment);
    void inject(AboutFragment aboutFragment);
    void inject(BellTableFragment bellTableFragment);
    void inject(WebViewFragment webViewFragment);
}
