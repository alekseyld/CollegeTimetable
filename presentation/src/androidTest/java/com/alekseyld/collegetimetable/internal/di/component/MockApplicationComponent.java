package com.alekseyld.collegetimetable.internal.di.component;

import com.alekseyld.collegetimetable.internal.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface MockApplicationComponent extends ApplicationComponent {

}