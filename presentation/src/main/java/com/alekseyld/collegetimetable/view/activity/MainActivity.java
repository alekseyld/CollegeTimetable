package com.alekseyld.collegetimetable.view.activity;

import android.os.Bundle;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.DaggerMainComponent;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.internal.di.module.MainModule;
import com.alekseyld.collegetimetable.view.activity.base.BaseActivity;
import com.alekseyld.collegetimetable.view.fragment.TableFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment(TableFragment.newInstance());
    }

    @Override
    protected MainComponent initializeInjections() {
        return DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainModule(new MainModule())
                .build();
    }
}
