package com.alekseyld.collegetimetable.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.DaggerMainComponent;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.internal.di.module.MainModule;
import com.alekseyld.collegetimetable.service.UpdateTimetableService;
import com.alekseyld.collegetimetable.view.activity.base.BaseActivity;
import com.alekseyld.collegetimetable.view.fragment.SettingsFragment;
import com.alekseyld.collegetimetable.view.fragment.SettingsFragmentPreference;
import com.alekseyld.collegetimetable.view.fragment.TableFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!UpdateTimetableService.isRunning){
            startService(new Intent(this, UpdateTimetableService.class));
        }

        addFragment(TableFragment.newInstance());

//        FlowManager.init(new FlowConfig.Builder(this)
//                .openDatabasesOnInit(true).build());
    }

    @Override
    protected MainComponent initializeInjections() {
        return DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainModule(new MainModule(this))
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_settings:
//                replaceFragment(SettingsFragment.newInstance());
                replaceFragment(SettingsFragmentPreference.newInstance());
                return true;

            case android.R.id.home:
                onBackPressed();
                return(true);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(this.getSupportFragmentManager().getBackStackEntryCount() > 0){
            this.getSupportFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }
    }
}
