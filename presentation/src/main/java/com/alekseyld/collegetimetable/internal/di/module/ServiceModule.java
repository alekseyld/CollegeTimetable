package com.alekseyld.collegetimetable.internal.di.module;

import static android.content.Context.MODE_PRIVATE;
import static com.alekseyld.collegetimetable.internal.di.module.ApplicationModule.HOST_PROXY;
import static com.alekseyld.collegetimetable.internal.di.module.ApplicationModule.HOST_SETTINGS;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;

import android.content.Context;
import android.content.SharedPreferences;

import com.alekseyld.collegetimetable.UIThread;
import com.alekseyld.collegetimetable.api.SettingsApi;
import com.alekseyld.collegetimetable.executor.JobExecutor;
import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;
import com.alekseyld.collegetimetable.repository.SettingsRepositoryImpl;
import com.alekseyld.collegetimetable.repository.TableRepositoryImpl;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;
import com.alekseyld.collegetimetable.repository.base.TableRepository;
import com.alekseyld.collegetimetable.service.GroupService;
import com.alekseyld.collegetimetable.service.GroupServiceImpl;
import com.alekseyld.collegetimetable.service.SettingsService;
import com.alekseyld.collegetimetable.service.SettingsServiceImpl;
import com.alekseyld.collegetimetable.service.TableService;
import com.alekseyld.collegetimetable.service.TableServiceImpl;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alekseyld on 05.11.2016.
 */


@Module
public class ServiceModule {

    private final Context mService;

    public ServiceModule(Context service){
        mService = service;
    }

    @Singleton
    @Provides
    SharedPreferences getSharedPreferences(){
        return mService.getSharedPreferences(NAME_FILE, MODE_PRIVATE);
    }

    @Singleton
    @Provides
    Context getService(){
        return mService;
    }

    @Singleton
    @Provides
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Singleton
    @Provides
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Singleton
    @Provides
    SettingsService provideSettingsService(SettingsServiceImpl settingsService){
        return settingsService;
    }

    @Singleton
    @Provides
    SettingsRepository provideSettingsRepository(SettingsRepositoryImpl settingsRepository){
        return settingsRepository;
    }

    @Singleton
    @Provides
    GroupService provideGroupService(GroupServiceImpl groupService){
        return groupService;
    }

    @Singleton
    @Provides
    TableService provideTableService(TableServiceImpl tableService){
        return tableService;
    }

    @Singleton
    @Provides
    TableRepository provideTableRepository(TableRepositoryImpl tableRepository){
        return tableRepository;
    }

    @Singleton
    @Provides
    SettingsApi provideSettingsApi(@Named("settings") Retrofit restAdapter){
        return restAdapter.create(SettingsApi.class);
    }

    @Provides @Singleton @Named("proxy")
    Retrofit provideRestAdapter(){
        return new Retrofit.Builder()
                .baseUrl(HOST_PROXY)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides @Singleton @Named("settings") Retrofit provideRestSettingsAdapter(){
        return new Retrofit.Builder()
                .baseUrl(HOST_SETTINGS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

}
