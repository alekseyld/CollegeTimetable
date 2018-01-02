package com.alekseyld.collegetimetable.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.presenter.BellTablePresenter;
import com.alekseyld.collegetimetable.view.BellView;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 12.10.2016.
 */

public class BellTableFragment extends BaseFragment<BellTablePresenter> implements BellView {

    public static BellTableFragment newInstance(){
        return new BellTableFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_belltable, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle(R.string.belltable_title);

        return v;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void initializeInjections() {
        getComponent(MainComponent.class).inject(this);
    }
}
