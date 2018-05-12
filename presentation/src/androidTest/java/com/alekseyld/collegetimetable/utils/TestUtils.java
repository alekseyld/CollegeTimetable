package com.alekseyld.collegetimetable.utils;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.TimeUnit;

/**
 * Created by Alekseyld on 16.09.2017.
 */

public class TestUtils {

    private static IdlingResource getIdlingResource(int time, TimeUnit timeUnit){
        IdlingPolicies.setMasterPolicyTimeout(
                time, timeUnit);
        IdlingPolicies.setIdlingResourceTimeout(
                time, timeUnit);

        IdlingResource idlingResource = new ElapsedTimeIdlingResource(timeUnit.toMillis(time));
        Espresso.registerIdlingResources(idlingResource);

        return idlingResource;
    }

    private static void unsleepEspresso(IdlingResource idlingResource){
        Espresso.unregisterIdlingResources(idlingResource);
    }

    public static void sleepEspresso(int time, TimeUnit timeUnit){
        IdlingResource idlingResource = getIdlingResource(5000, TimeUnit.MILLISECONDS);
        while (!idlingResource.isIdleNow());
        unsleepEspresso(idlingResource);
    }

    public static LessonViewMatcher withDayLesson(int dayPos){
        return new LessonViewMatcher(dayPos);
    }

}
