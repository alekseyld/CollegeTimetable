package com.alekseyld.collegetimetable.repository.base;

import com.alekseyld.collegetimetable.entity.User;

/**
 * Created by Alekseyld on 03.01.2018.
 */

public interface UserRepository {

    String USER_KEY = "USER_KEY";

    User getUser();
    boolean putUser(User user);
    void updateUser(User user);

}
