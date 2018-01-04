package com.alekseyld.collegetimetable.repository;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.alekseyld.collegetimetable.entity.User;
import com.alekseyld.collegetimetable.repository.base.UserRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 03.01.2018.
 */

public class UserRepositoryImpl implements UserRepository {

    private Gson mGson;
    private Type mUserType;
    private SharedPreferences mPref;

    @Inject
    UserRepositoryImpl(SharedPreferences sharedPreferences){
        mPref = sharedPreferences;

        mGson = new Gson();
        mUserType = new TypeToken<User>(){}.getType();
    }

    @Override
    public User getUser() {
        String json = mPref.getString(USER_KEY, "");
        return mGson.fromJson(json, mUserType);
    }

    @Override
    public boolean putUser(User user) {
        String json = mGson.toJson(user);
        SharedPreferences.Editor ed = mPref.edit();
        ed.putString(USER_KEY, json);
        ed.apply();
        return true;
    }

    @Override
    public void updateUser(@NonNull User user) {
        User oldUser = getUser();

        if (oldUser == null) {
            putUser(user);
            return;
        }

        oldUser = oldUser.update(user);
        putUser(oldUser);
    }

    @Override
    public boolean deleteUser() {
        if (!mPref.contains(USER_KEY)){
            return false;
        }

        mPref.edit().remove(USER_KEY).apply();
        return true;
    }
}
