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
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Alekseyld on 02.09.2016.
 */

@Module
public class ApplicationModule {
    private final AndroidApplication application;
    private static final String HOST = "http://noblockme.ru/api/";

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
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides @Singleton @Named("server") Retrofit provideServerRestAdapter(){
        return new Retrofit.Builder()
                //todo host from shared pref
                .baseUrl("http://192.168.0.100/collegetimetable/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}
