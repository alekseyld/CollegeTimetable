package com.alekseyld.collegetimetable.view;

import com.alekseyld.collegetimetable.TableWrapper;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public interface TableView extends ViewWithSettingsPresenter {

    void setTimeTable(TableWrapper timeTable);
    String getGroup();

}
