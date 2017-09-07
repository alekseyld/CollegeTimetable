package com.alekseyld.collegetimetable.view.activity.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.alekseyld.collegetimetable.internal.di.HasComponent;

/**
 * Created by Alekseyld on 07.09.2017.
 */

public abstract class BaseInjectorActivity<TInjector> extends BaseActivity implements HasComponent<TInjector> {

    protected TInjector mInjectorComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInjectorComponent = initializeInjections();
        initialize();
    }

    protected abstract TInjector initializeInjections();

    @CallSuper
    protected void initialize() {
        initializeInjections();
    }

    @Override
    public TInjector getComponent() {
        return mInjectorComponent;
    }


}