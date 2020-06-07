package com.alekseyld.collegetimetable.internal.di.module;

import android.content.Context;

import com.alekseyld.collegetimetable.AndroidApplication;
import com.alekseyld.collegetimetable.UIThread;
import com.alekseyld.collegetimetable.executor.JobExecutor;
import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alekseyld on 02.09.2016.
 */

@Module
public class ApplicationModule {
    private final AndroidApplication application;
    static final String HOST_PROXY = "https://noblockme.ru/api/";
    static final String HOST_SETTINGS = "https://alekseyld.github.io/CollegeTimetable/";

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton @Named("proxy") Retrofit provideRestAdapter(){
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
