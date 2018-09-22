package com.alekseyld.collegetimetable.internal.di.component;

import com.alekseyld.collegetimetable.internal.di.PerActivity;
import com.alekseyld.collegetimetable.internal.di.module.MainModule;

import dagger.Component;

@PerActivity
@Component(dependencies = {ApplicationComponent.class}, modules = {MainModule.class})
public interface MockMainComponent extends MainComponent {

}