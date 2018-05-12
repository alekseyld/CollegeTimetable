package com.alekseyld.collegetimetable.view;

import android.graphics.Bitmap;

import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.view.adapter.TableAdapter;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public interface TableView extends ViewWithSettingsPresenter {

    void setTimeTable(TimeTable timeTable);
    TimeTable getTimeTable();
    TableAdapter getTableAdapter();
    void showMessage();
    String getGroup();

    boolean getChangeMode();
    void shareDay(Bitmap image);

}
