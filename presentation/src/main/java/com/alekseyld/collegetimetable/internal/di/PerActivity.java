package com.alekseyld.collegetimetable.internal.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Alekseyld on 02.09.2016.
 */

@Scope
@Retention(RUNTIME)
public @interface PerActivity {}

