package com.alekseyld.collegetimetable.view.activity.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.alekseyld.collegetimetable.AndroidApplication;
import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.ApplicationComponent;
import com.alekseyld.collegetimetable.internal.di.module.ActivityModule;
import com.google.firebase.analytics.FirebaseAnalytics;

import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.DARK_MODE_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity{

    public static boolean isDarkMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(NAME_FILE, MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean(DARK_MODE_KEY, false);

        FirebaseAnalytics.getInstance(this)
                .setUserProperty("dark_mode", Boolean.toString(isDarkMode));

        if (isDarkMode) {
            setTheme(R.style.DarkAppTheme);
        } else  {
            setTheme(R.style.AppTheme);
        }
    }

    protected void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(getContainerId(), fragment);
        fragmentTransaction.commit();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(getContainerId(), fragment, fragment.getClass().getName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    protected int getContainerId() {
        return R.id.fragmentFrame;
    }


    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    public ActionBar getActionBarBase() {
        return getSupportActionBar();
    }

    protected void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void startActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }

}
