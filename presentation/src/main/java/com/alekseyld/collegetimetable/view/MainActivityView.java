package com.alekseyld.collegetimetable.view;

import com.alekseyld.collegetimetable.entity.User;

/**
 * Created by Alekseyld on 04.01.2018.
 */

public interface MainActivityView {

    void startLoginActivity();
    void buildMenu(String[] favorite);
    void setUserInfo(User user);

}
