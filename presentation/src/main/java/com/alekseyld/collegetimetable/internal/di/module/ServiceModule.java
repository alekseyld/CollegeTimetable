package com.alekseyld.collegetimetable.internal.di.module;

import android.app.IntentService;
import android.content.SharedPreferences;

import com.alekseyld.collegetimetable.UIThread;
import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.executor.JobExecutor;
import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;
import com.alekseyld.collegetimetable.repository.NotificationRepositoryImpl;
import com.alekseyld.collegetimetable.repository.SettingsRepositoryImpl;
import com.alekseyld.collegetimetable.repository.TableRepositoryImpl;
import com.alekseyld.collegetimetable.repository.UserRepositoryImpl;
import com.alekseyld.collegetimetable.repository.base.NotificationRepository;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;
import com.alekseyld.collegetimetable.repository.base.TableRepository;
import com.alekseyld.collegetimetable.repository.base.UserRepository;
import com.alekseyld.collegetimetable.service.ServerService;
import com.alekseyld.collegetimetable.service.ServerServiceImpl;
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
 * Created by Alekseyld on 05.11.2017.
 */


@Module
public class ServiceModule {

    private final IntentService mService;

    public ServiceModule(IntentService service){
        mService = service;
    }

    @Singleton
    @Provides
    SharedPreferences getSharedPreferences(){
        return mService.getSharedPreferences(NAME_FILE, MODE_PRIVATE);
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
    ServerService provideSettingsService(ServerServiceImpl serverService){
        return serverService;
    }

    @Singleton
    @Provides
    SettingsRepository provideSettingsRepository(SettingsRepositoryImpl settingsRepository){
        return settingsRepository;
    }

    @Singleton
    @Provides
    TableRepository provideTableRepository(TableRepositoryImpl tableRepository){
        return tableRepository;
    }

    @Singleton
    @Provides
    UserRepository provideUserRepository(UserRepositoryImpl userRepository){
        return userRepository;
    }

    @Singleton
    @Provides
    NotificationRepository provideNotificationRepository(NotificationRepositoryImpl notificationRepository){
        return notificationRepository;
    }

    @Provides @Singleton @Named("server") Retrofit provideServerRestAdapter(){
        //fixme сделать по-нормальному
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
                .create();

        String json = getService().getSharedPreferences(NAME_FILE, MODE_PRIVATE)
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
