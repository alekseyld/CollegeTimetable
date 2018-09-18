package com.alekseyld.collegetimetable.internal.di.component;

import com.alekseyld.collegetimetable.internal.di.module.ServiceModule;
import com.alekseyld.collegetimetable.job.TimetableJob;
import com.alekseyld.collegetimetable.service.UpdateTimetableService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Alekseyld on 05.11.2016.
 */

@Singleton
@Component(modules=ServiceModule.class)
public interface ServiceComponent {
    void inject(UpdateTimetableService service);
    void inject(TimetableJob service);
}
