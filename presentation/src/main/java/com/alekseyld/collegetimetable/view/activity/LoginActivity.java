package com.alekseyld.collegetimetable.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.User;
import com.alekseyld.collegetimetable.internal.di.component.DaggerLoginComponent;
import com.alekseyld.collegetimetable.internal.di.component.LoginComponent;
import com.alekseyld.collegetimetable.internal.di.module.MainModule;
import com.alekseyld.collegetimetable.view.activity.base.BaseInjectorActivity;
import com.alekseyld.collegetimetable.view.fragment.LoginFragment;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;
import static com.alekseyld.collegetimetable.repository.base.UserRepository.USER_KEY;

/**
 * Created by Alekseyld on 02.01.2018.
 */

public class LoginActivity extends BaseInjectorActivity<LoginComponent> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences preferences = getSharedPreferences(NAME_FILE, MODE_PRIVATE);
        if (preferences.contains(USER_KEY)) {
            String json = preferences.getString(USER_KEY, "");
            User user = new Gson().fromJson(json, User.class);

            if (user.getAuthKey() != null && !user.getAuthKey().equals("")) {
                startActivity(new Intent(this, MainActivity.class));
                return;
            }
        }

        addFragment(LoginFragment.newInstance());
    }

    @Override
    protected LoginComponent initializeInjections() {
        return DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainModule(new MainModule(this))
                .build();
    }

}
