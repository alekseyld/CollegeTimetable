package com.alekseyld.collegetimetable.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.DaggerMainComponent;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.internal.di.module.MainModule;
import com.alekseyld.collegetimetable.service.UpdateTimetableService;
import com.alekseyld.collegetimetable.view.activity.base.BaseActivity;
import com.alekseyld.collegetimetable.view.fragment.AboutFragment;
import com.alekseyld.collegetimetable.view.fragment.SettingsFragment;
import com.alekseyld.collegetimetable.view.fragment.TableFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.nav_view)
    NavigationView navigation;

    @BindView(R.id.drawer)
    DrawerLayout drawer;

    private MainNavigationViewItemSelectedListener mOnNavigationItemSelectedListener;

    private class MainNavigationViewItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            if (menuItem != null) {
                if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                    getSupportFragmentManager().popBackStack();
                }
                int id = menuItem.getItemId();
                return onItemSelected(id, false);
            }
            return false;
        }

        private boolean onItemSelected(int id, boolean force) {
            hideKeyboard();
            switch (id) {
                case R.id.settings:
                    replaceFragment(SettingsFragment.newInstance());
                    break;
                case R.id.about:
                    replaceFragment(AboutFragment.newInstance());
                    break;
            }
            drawer.closeDrawer(navigation);
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(!UpdateTimetableService.isRunning){
            startService(new Intent(this, UpdateTimetableService.class));
        }

        addFragment(TableFragment.newInstance());

//        FlowManager.init(new FlowConfig.Builder(this)
//                .openDatabasesOnInit(true).build());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mOnNavigationItemSelectedListener == null) {
            mOnNavigationItemSelectedListener = new MainNavigationViewItemSelectedListener();
        }
        navigation.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected MainComponent initializeInjections() {
        return DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainModule(new MainModule(this))
                .build();
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
