package com.alekseyld.collegetimetable.internal.di.component;

import android.content.Context;

import com.alekseyld.collegetimetable.executor.PostExecutionThread;
import com.alekseyld.collegetimetable.executor.ThreadExecutor;
import com.alekseyld.collegetimetable.internal.di.module.ApplicationModule;
import com.alekseyld.collegetimetable.view.activity.base.BaseActivity;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by Alekseyld on 02.09.2016.
 */

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    @Named("proxy") Retrofit provideRestAdapter();
    @Named("server") Retrofit provideServerRestAdapter();
}
