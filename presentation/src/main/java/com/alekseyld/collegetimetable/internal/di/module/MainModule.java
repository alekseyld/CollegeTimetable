package com.alekseyld.collegetimetable.internal.di.module;

import android.app.Activity;
import android.content.SharedPreferences;

import com.alekseyld.collegetimetable.internal.di.PerActivity;
import com.alekseyld.collegetimetable.navigator.NavigatorImpl;
import com.alekseyld.collegetimetable.navigator.base.SettingsResultProcessor;
import com.alekseyld.collegetimetable.repository.SettingsRepositoryImpl;
import com.alekseyld.collegetimetable.repository.TableRepositoryImpl;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;
import com.alekseyld.collegetimetable.repository.base.TableRepository;
import com.alekseyld.collegetimetable.service.SettingsService;
import com.alekseyld.collegetimetable.service.SettingsServiceImpl;
import com.alekseyld.collegetimetable.service.TableService;
import com.alekseyld.collegetimetable.service.TableServiceImpl;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;

/**
 * Created by Alekseyld on 02.09.2016.
 */

@Module
public class MainModule {

    private final Activity activity;

    public MainModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    SharedPreferences getSharedPreferences(){
        return activity.getSharedPreferences(NAME_FILE, MODE_PRIVATE);
    }

    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }

    @PerActivity @Provides
    TableService provideTableService(TableServiceImpl tableService){
        return tableService;
    }

    @PerActivity @Provides
    SettingsService provideSettingsService(SettingsServiceImpl settingsService){
        return settingsService;
    }

    @PerActivity @Provides
    TableRepository provideTableRepository(TableRepositoryImpl tableRepository){
        return tableRepository;
    }

    @PerActivity @Provides
    SettingsRepository provideSettingsRepository(SettingsRepositoryImpl settingsRepository){
        return settingsRepository;
    }

    @PerActivity @Provides
    SettingsResultProcessor provideSettingsResultProcessor(NavigatorImpl navigator){
        return navigator;
    }
}
