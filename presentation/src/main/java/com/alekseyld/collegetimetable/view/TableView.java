package com.alekseyld.collegetimetable.view;

import android.graphics.Bitmap;

import com.alekseyld.collegetimetable.entity.TimeTable;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public interface TableView extends ViewWithSettingsPresenter {

    void setTimeTable(TimeTable timeTable);
    TimeTable getTimeTable();
    void showMessage();
    String getGroup();

    boolean getChangeMode();
    void shareDay(Bitmap image);

}
