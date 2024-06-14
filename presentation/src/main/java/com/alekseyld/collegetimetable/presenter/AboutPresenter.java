package com.alekseyld.collegetimetable.presenter;

import android.content.SharedPreferences;

import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.view.AboutView;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 05.11.2016.
 */

public class AboutPresenter extends BasePresenter<AboutView> {

    SharedPreferences mSharedPreferences;

    @Inject
    public AboutPresenter(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public String getDebugText() {
        StringBuilder keys = new StringBuilder();

        for (String key : mSharedPreferences.getAll().keySet())
            keys.append("-").append(key).append("\n");

        return "Объекты в базе данных: " + "\n" + keys;
    }

}
