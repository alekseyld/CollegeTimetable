package com.alekseyld.collegetimetable.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;

import com.alekseyld.collegetimetable.databinding.ActivityFavoriteBinding;
import com.alekseyld.collegetimetable.internal.di.component.DaggerSettingsFavoriteComponent;
import com.alekseyld.collegetimetable.internal.di.component.SettingsFavoriteComponent;
import com.alekseyld.collegetimetable.internal.di.module.MainModule;
import com.alekseyld.collegetimetable.view.activity.base.BaseInjectorActivity;
import com.alekseyld.collegetimetable.view.fragment.SettingsFavoriteFragment;


/**
 * Created by Alekseyld on 07.09.2017.
 */
public class SettingsFavoriteActivity extends BaseInjectorActivity<SettingsFavoriteComponent> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFavoriteBinding binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarInclude.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        binding.toolbarInclude.toolbar.setNavigationOnClickListener(v -> finish());

        boolean teacherMode = false;
        if (getIntent().getExtras() != null) {
            teacherMode = getIntent().getExtras().getBoolean("teacherMode", false);
        }

        addFragment(SettingsFavoriteFragment.newInstance(teacherMode));
    }

    @Override
    protected SettingsFavoriteComponent initializeInjections() {
        return DaggerSettingsFavoriteComponent.builder()
            .applicationComponent(getApplicationComponent())
            .mainModule(new MainModule(this))
            .build();
    }
}
