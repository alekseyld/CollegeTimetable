package com.alekseyld.collegetimetable.internal.di.component;

import android.app.Activity;

import com.alekseyld.collegetimetable.internal.di.PerActivity;
import com.alekseyld.collegetimetable.internal.di.module.ActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
  Activity activity();
}
