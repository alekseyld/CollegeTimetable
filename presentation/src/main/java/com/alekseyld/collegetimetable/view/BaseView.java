package com.alekseyld.collegetimetable.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;

import com.alekseyld.collegetimetable.view.activity.base.BaseActivity;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public interface BaseView {

    void showLoading();

    void hideLoading();

    void showError(String message);

    void showAlertDialog(String title,
                         @Nullable String text,
                         String positiveText,
                         String negativeText,
                         DialogInterface.OnClickListener positiveOperation,
                         @Nullable  DialogInterface.OnClickListener negativeOperation);

    Context getContext();

    BaseActivity getBaseActivity();

}
