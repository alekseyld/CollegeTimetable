package com.alekseyld.collegetimetable.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.User;
import com.alekseyld.collegetimetable.internal.di.component.DaggerMainComponent;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.internal.di.module.MainModule;
import com.alekseyld.collegetimetable.presenter.MainActivityPresenter;
import com.alekseyld.collegetimetable.view.MainActivityView;
import com.alekseyld.collegetimetable.view.activity.base.BaseInjectorActivity;
import com.alekseyld.collegetimetable.view.fragment.AboutFragment;
import com.alekseyld.collegetimetable.view.fragment.BellTableFragment;
import com.alekseyld.collegetimetable.view.fragment.CertificateFragment;
import com.alekseyld.collegetimetable.view.fragment.NotificationsFragment;
import com.alekseyld.collegetimetable.view.fragment.SettingsFragment;
import com.alekseyld.collegetimetable.view.fragment.TableFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseInjectorActivity<MainComponent> implements MainActivityView {

    @BindView(R.id.nav_view)
    NavigationView navigation;

    @BindView(R.id.drawer)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private MainNavigationViewItemSelectedListener mOnNavigationItemSelectedListener;
    private ActionBarDrawerToggle drawerToggle;

    @Inject
    MainActivityPresenter mPresenter;

    private String[] favorite;

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

            if (id == R.id.action_notification) {
                replaceFragment(NotificationsFragment.newInstance());
                drawer.closeDrawer(navigation);
                return false;
            }

            if (id == R.id.action_certificate) {
                replaceFragment(CertificateFragment.newInstance());
                drawer.closeDrawer(navigation);
                return false;
            }

            if (id == R.id.action_settings) {
                replaceFragment(SettingsFragment.newInstance(false));
                drawer.closeDrawer(navigation);
                return false;
            }
            if (id == R.id.about) {
                replaceFragment(AboutFragment.newInstance());
                drawer.closeDrawer(navigation);
                return false;
            }

            if (id == R.id.action_exit) {
                mPresenter.prepareExit();
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

        //buildMenu();
        initializeInjection();

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

        processMenu();

        if (mOnNavigationItemSelectedListener == null) {
            mOnNavigationItemSelectedListener = new MainNavigationViewItemSelectedListener();
        }
        navigation.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    protected void processMenu() {
        mPresenter.getAndSetFavorites();
        mPresenter.getAndSetUserInfo();
    }

    public void setUserInfo(User user) {
        if (user.getGroup() != null) {
            TextView group = (TextView) navigation.getHeaderView(0).findViewById(R.id.header_my_group);
            group.setText(user.getGroup());
        }

        if (user.getName() != null) {
            TextView name = (TextView) navigation.getHeaderView(0).findViewById(R.id.name_user);
            name.setText(user.getNameWithSurname());
        }
    }

    public void buildMenu(String[] favorite) {
        this.favorite = favorite;

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
        menu.add(Menu.NONE, R.id.action_notification, Menu.NONE, R.string.action_notification);
        menu.add(Menu.NONE, R.id.action_certificate, Menu.NONE, R.string.action_certificate);
        menu.add(Menu.NONE, R.id.action_settings, Menu.NONE, R.string.action_settings);
        menu.add(Menu.NONE, R.id.action_exit, Menu.NONE, R.string.action_exit);
        menu.add(Menu.NONE, R.id.about, Menu.NONE, R.string.about);

        menu.getItem(menu.size() - 6).setIcon(R.drawable.ic_access_time);
        menu.getItem(menu.size() - 5).setIcon(R.drawable.ic_notifications_black_24dp);
        menu.getItem(menu.size() - 4).setIcon(R.drawable.ic_certificate_black);
        menu.getItem(menu.size() - 3).setIcon(R.drawable.ic_settings);
        menu.getItem(menu.size() - 2).setIcon(R.drawable.ic_exit_to_app_black);
        menu.getItem(menu.size() - 1).setIcon(R.drawable.ic_information);

        if (getSupportFragmentManager().findFragmentByTag(SettingsFragment.class.getName()) != null){
            menu.getItem(menu.size() - 2).setChecked(true);
        } else {
            menu.getItem(0).setChecked(true);
        }
    }

    @Override
    public void startLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
        finish();
    }

    public void rebuildMenu() {
        processMenu();
    }

    @Override
    protected MainComponent initializeInjections() {
        return DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainModule(new MainModule(this))
                .build();
    }

    protected void initializeInjection() {
        getComponent().inject(this);
        mPresenter.setView(this);
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
