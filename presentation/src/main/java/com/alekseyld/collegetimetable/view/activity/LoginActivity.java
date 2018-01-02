package com.alekseyld.collegetimetable.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.DaggerLoginComponent;
import com.alekseyld.collegetimetable.internal.di.component.LoginComponent;
import com.alekseyld.collegetimetable.internal.di.module.MainModule;
import com.alekseyld.collegetimetable.view.activity.base.BaseInjectorActivity;
import com.alekseyld.collegetimetable.view.fragment.LoginFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        //todo если вход выполнен переходить сразу на MainActivity
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
