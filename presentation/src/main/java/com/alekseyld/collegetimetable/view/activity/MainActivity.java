package com.alekseyld.collegetimetable.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.DaggerMainComponent;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.internal.di.module.MainModule;
import com.alekseyld.collegetimetable.view.activity.base.BaseActivity;
import com.alekseyld.collegetimetable.view.fragment.AboutFragment;
import com.alekseyld.collegetimetable.view.fragment.BellTableFragment;
import com.alekseyld.collegetimetable.view.fragment.SettingsFragment;
import com.alekseyld.collegetimetable.view.fragment.TableFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.FAVORITEGROUPS_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;

public class MainActivity extends BaseActivity {

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

            if(id == R.id.action_belltable){
                replaceFragment(BellTableFragment.newInstance());
                drawer.closeDrawer(navigation);
                return false;
            }

            if(id == R.id.action_settings) {
                replaceFragment(SettingsFragment.newInstance());
                drawer.closeDrawer(navigation);
                return false;
            }
            if(id == R.id.about) {
                replaceFragment(AboutFragment.newInstance());
                drawer.closeDrawer(navigation);
                return false;
            }

            if(id < favorite.length){
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

    @Override
    protected void buildMenu(){

        SharedPreferences preferences = getSharedPreferences(NAME_FILE, MODE_PRIVATE);

        if(preferences.contains(FAVORITEGROUPS_KEY)) {
            String json = preferences.getString(FAVORITEGROUPS_KEY, "");
            Set<String> set = new Gson().fromJson(json,
                    new TypeToken<Set<String>>(){}.getType());
            favorite = set.toArray(new String[set.size()]);
        }

        Menu menu = navigation.getMenu();
        menu.clear();
        if(favorite != null) {
            for (int i = 0; i < favorite.length; i++) {
                menu.add(Menu.NONE, i, Menu.NONE, favorite[i]);
            }
        }

        menu.add(Menu.NONE, R.id.action_belltable, Menu.NONE, R.string.action_belltable);
        menu.add(Menu.NONE, R.id.action_settings, Menu.NONE, R.string.action_settings);
        menu.add(Menu.NONE, R.id.about, Menu.NONE, R.string.about);

        menu.getItem(menu.size() - 3).setIcon(R.drawable.ic_access_time_black_24dp);
        menu.getItem(menu.size() - 2).setIcon(R.drawable.ic_settings);
        menu.getItem(menu.size() - 1).setIcon(R.drawable.ic_information);
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
