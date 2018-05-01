package com.alekseyld.collegetimetable.view;

import com.alekseyld.collegetimetable.view.adapter.FavoriteGroupAdapter;

/**
 * Created by Alekseyld on 07.09.2017.
 */

public interface SettingsFavoriteView extends BaseView {
    FavoriteGroupAdapter getAdapter();
    void setMessage(String mes);
    boolean getTeacherMode();
}
