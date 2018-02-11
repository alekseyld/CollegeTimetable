package com.alekseyld.collegetimetable.view;

import com.alekseyld.collegetimetable.entity.TimeTable;

/**
 * Created by Alekseyld on 02.09.2017.
 */

public interface TableView extends ViewWithSettingsPresenter {

    void setTimeTable(TimeTable timeTable);
    TimeTable getTimeTable();
    void showMessage();
    String getGroupOrTeacher();

}
