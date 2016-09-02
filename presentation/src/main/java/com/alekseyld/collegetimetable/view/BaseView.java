package com.alekseyld.collegetimetable.view;

import android.content.Context;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public interface BaseView {

    void showLoading();

    void hideLoading();

    void showError(String message);

    Context context();

}
