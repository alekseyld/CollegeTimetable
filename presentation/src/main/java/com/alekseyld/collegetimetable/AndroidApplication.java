package com.alekseyld.collegetimetable;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.StrictMode;

import com.alekseyld.collegetimetable.internal.di.component.ApplicationComponent;
import com.alekseyld.collegetimetable.internal.di.component.DaggerApplicationComponent;
import com.alekseyld.collegetimetable.internal.di.module.ApplicationModule;
import com.alekseyld.collegetimetable.job.TimetableJob;
import com.alekseyld.collegetimetable.job.TimetableJobCreator;
import com.alekseyld.collegetimetable.utils.Utils;
import com.crashlytics.android.Crashlytics;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.util.Set;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class AndroidApplication extends Application {

    private final String NAME_FILE = "DataStorage";
    private final String NOTIFON_KEY = "NotifOn";

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        BackwardCompatibility.checkAppVersion(this.getSharedPreferences(NAME_FILE, MODE_PRIVATE));

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Fabric.with(this, new Crashlytics());

        JobManager.create(this).addJobCreator(new TimetableJobCreator());

        initializeInjector();
        initializeDBFlow();
        initializeService();
    }

    private void initializeService() {
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(TimetableJob.TAG);
        SharedPreferences preferences = this.getSharedPreferences(NAME_FILE, MODE_PRIVATE);
        if (preferences.getBoolean(NOTIFON_KEY, false) &&
                jobRequests.size() == 0) {
            
            Utils.getTimeTableJob().schedule();
        }
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void initializeDBFlow() {
//        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
