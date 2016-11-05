package com.alekseyld.collegetimetable.internal.di.module;

import android.app.IntentService;

import com.alekseyld.collegetimetable.UIThread;
import com.alekseyld.collegetimetable.executor.JobExecutor;
import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;
import com.alekseyld.collegetimetable.repository.ServiceSettingsRepository;
import com.alekseyld.collegetimetable.repository.ServiceTableRepository;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;
import com.alekseyld.collegetimetable.repository.base.TableRepository;
import com.alekseyld.collegetimetable.service.SettingsService;
import com.alekseyld.collegetimetable.service.SettingsServiceImpl;
import com.alekseyld.collegetimetable.service.TableService;
import com.alekseyld.collegetimetable.service.TableServiceImpl;

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

    private final IntentService mService;
    private static final String HOST = "http://noblockme.ru/api/";

    public ServiceModule(IntentService service){
        mService = service;
    }

    @Singleton
    @Provides
    IntentService getService(){
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
    SettingsRepository provideSettingsRepository(ServiceSettingsRepository settingsRepository){
        return settingsRepository;
    }

    @Singleton
    @Provides
    TableService provideTableService(TableServiceImpl tableService){
        return tableService;
    }

    @Singleton
    @Provides
    TableRepository provideTableRepository(ServiceTableRepository tableRepository){
        return tableRepository;
    }

    @Provides @Singleton
    Retrofit provideRestAdapter(){
        return new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

}
