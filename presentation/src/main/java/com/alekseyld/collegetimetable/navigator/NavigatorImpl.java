package com.alekseyld.collegetimetable.navigator;

import com.alekseyld.collegetimetable.navigator.base.Navigator;
import com.alekseyld.collegetimetable.view.activity.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class NavigatorImpl implements Navigator {

    @Inject
    public NavigatorImpl() {
    }

    @Override
    public void processSettingsResult(BaseActivity activity) {
        activity.getSupportFragmentManager().popBackStack();
    }
}
