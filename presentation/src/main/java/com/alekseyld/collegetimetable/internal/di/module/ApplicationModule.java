package com.alekseyld.collegetimetable.internal.di.module;

import android.content.Context;

import com.alekseyld.collegetimetable.AndroidApplication;
import com.alekseyld.collegetimetable.UIThread;
import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.executor.JobExecutor;
import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.SETTINGS_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;

/**
 * Created by Alekseyld on 02.09.2017.
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
        //fixme сделать по-нормальному
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        String json = application.getSharedPreferences(NAME_FILE, MODE_PRIVATE)
                .getString(SETTINGS_KEY, "");
        String url = gson.fromJson(json, Settings.class).getUrlServer();

        if (url == null)
            url = "http://www.example.org";

        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}
