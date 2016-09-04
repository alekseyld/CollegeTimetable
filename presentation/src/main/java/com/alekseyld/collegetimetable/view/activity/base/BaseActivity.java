package com.alekseyld.collegetimetable.view.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.alekseyld.collegetimetable.AndroidApplication;
import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.HasComponent;
import com.alekseyld.collegetimetable.internal.di.component.ActivityComponent;
import com.alekseyld.collegetimetable.internal.di.component.ApplicationComponent;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.internal.di.module.ActivityModule;
import com.alekseyld.collegetimetable.navigator.base.Navigator;

import javax.inject.Inject;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity implements HasComponent<MainComponent> {

//  @Inject
//  Navigator navigator;

  protected MainComponent mComponent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mComponent = initializeInjections();
//    this.getApplicationComponent().inject(this);
  }

  protected void addFragment(Fragment fragment) {
    FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
    fragmentTransaction.add(getContainerId(), fragment);
    fragmentTransaction.commit();
  }

  protected void replaceFragment(Fragment fragment) {
    FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(getContainerId(), fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }

  protected int getContainerId(){
    return R.id.fragmentFrame;
  }


  protected ApplicationComponent getApplicationComponent() {
    return ((AndroidApplication)getApplication()).getApplicationComponent();
  }

  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }

  protected abstract MainComponent initializeInjections();

  @Override
  public MainComponent getComponent() {
    return mComponent;
  }
}
