package com.alekseyld.collegetimetable.view.activity;

import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.internal.di.component.DaggerMainComponent;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.internal.di.module.MainModule;
import com.alekseyld.collegetimetable.view.activity.base.BaseInjectorActivity;
import com.alekseyld.collegetimetable.view.fragment.AboutFragment;
import com.alekseyld.collegetimetable.view.fragment.BellTableFragment;
import com.alekseyld.collegetimetable.view.fragment.SettingsFragment;
import com.alekseyld.collegetimetable.view.fragment.TableFragment;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.SETTINGS_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;

public class MainActivity extends BaseInjectorActivity<MainComponent> {

    @BindView(R.id.nav_view)
    NavigationView navigation;

    @BindView(R.id.drawer)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String[] favorite;

    private MainNavigationViewItemSelectedListener mOnNavigationItemSelectedListener;
    private ActionBarDrawerToggle drawerToggle;

    private class MainNavigationViewItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                getSupportFragmentManager().popBackStack();
            }
            int id = menuItem.getItemId();

            int size = navigation.getMenu().size();
            for (int i = 0; i < size; i++) {
                navigation.getMenu().getItem(i).setChecked(false);
            }

            menuItem.setChecked(true);

            return onItemSelected(id, false);
        }

        private boolean onItemSelected(int id, boolean force) {
            hideKeyboard();

            if (id == R.id.action_belltable) {
                replaceFragment(BellTableFragment.newInstance());
                drawer.closeDrawer(navigation);
                return false;
            }

            if (id == R.id.action_settings) {
                replaceFragment(SettingsFragment.newInstance());
                drawer.closeDrawer(navigation);
                return false;
            }
            if (id == R.id.about) {
                replaceFragment(AboutFragment.newInstance());
                drawer.closeDrawer(navigation);
                return false;
            }

            if (id < favorite.length) {
                replaceFragment(TableFragment.newInstance(favorite[id]));
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

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.cancel(getIntent().getIntExtra("NOTIFICATION_ID", -1));
        }

        buildMenu();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string._0, R.string._1);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

//        if(!UpdateTimetableService.isRunning){
//            startService(new Intent(this, UpdateTimetableService.class));
//        }

        addFragment(TableFragment.newInstance(""));
    }

    @Override
    protected void onResume() {
        super.onResume();

        buildMenu();

        if (mOnNavigationItemSelectedListener == null) {
            mOnNavigationItemSelectedListener = new MainNavigationViewItemSelectedListener();
        }
        navigation.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    protected void buildMenu() {

        SharedPreferences preferences = getSharedPreferences(NAME_FILE, MODE_PRIVATE);

        if (preferences.contains(SETTINGS_KEY)) {
            String json = preferences.getString(SETTINGS_KEY, "");
            Settings settings = new Gson().fromJson(json, Settings.class);
            if (settings.getFavoriteGroups() != null) {
                favorite = settings.getFavoriteGroups().toArray(new String[0]);

                if (settings.getNotificationGroup() != null) {
                    TextView group = (TextView) navigation.getHeaderView(0).findViewById(R.id.header_my_group);
                    group.setText(settings.getNotificationGroup());
                }
            }
        }

        Menu menu = navigation.getMenu();
        menu.clear();

        menu.add(Menu.NONE, R.id.action_home, Menu.NONE, R.string.mygroup);
        menu.getItem(0).setIcon(R.drawable.ic_home_black_24dp);

        if (favorite != null) {
            for (int i = 0; i < favorite.length; i++) {
                menu.add(Menu.NONE, i, Menu.NONE, favorite[i]);
            }
        }

        menu.add(Menu.NONE, R.id.action_belltable, Menu.NONE, R.string.action_belltable);
        menu.add(Menu.NONE, R.id.action_settings, Menu.NONE, R.string.action_settings);
        menu.add(Menu.NONE, R.id.about, Menu.NONE, R.string.about);

        menu.getItem(menu.size() - 3).setIcon(R.drawable.ic_access_time);
        menu.getItem(menu.size() - 2).setIcon(R.drawable.ic_settings);
        menu.getItem(menu.size() - 1).setIcon(R.drawable.ic_information);

        if (getSupportFragmentManager().findFragmentByTag(SettingsFragment.class.getName()) != null){
            menu.getItem(menu.size() - 2).setChecked(true);
        } else {
            menu.getItem(0).setChecked(true);
        }

    }

    public void rebuildMenu() {
        buildMenu();
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
        if (this.getSupportFragmentManager().getBackStackEntryCount() > 0) {
            this.getSupportFragmentManager().popBackStack();
            rebuildMenu();
        } else {
            super.onBackPressed();
        }
    }
}
