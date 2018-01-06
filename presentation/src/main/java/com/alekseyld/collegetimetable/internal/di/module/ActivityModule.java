package com.alekseyld.collegetimetable.internal.di.module;

import android.app.Activity;

import com.alekseyld.collegetimetable.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alekseyld on 02.09.2017.
 */

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }
}