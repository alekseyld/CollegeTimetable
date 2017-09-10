package com.alekseyld.collegetimetable.view.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.alekseyld.collegetimetable.AndroidApplication;
import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.ApplicationComponent;
import com.alekseyld.collegetimetable.internal.di.module.ActivityModule;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(getContainerId(), fragment);
        fragmentTransaction.commit();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(getContainerId(), fragment);
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
