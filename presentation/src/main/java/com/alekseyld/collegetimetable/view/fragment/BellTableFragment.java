package com.alekseyld.collegetimetable.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.databinding.FragmentBelltableBinding;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.presenter.BellTablePresenter;
import com.alekseyld.collegetimetable.view.BellView;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;

/**
 * Created by Alekseyld on 12.10.2016.
 */

public class BellTableFragment extends BaseFragment<BellTablePresenter> implements BellView {

    private FragmentBelltableBinding binding;

    public static BellTableFragment newInstance(){
        return new BellTableFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBelltableBinding.inflate(inflater, container, false);
        getActivity().setTitle(R.string.belltable_title);

        return binding.getRoot();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    protected void initializeInjections() {
        getComponent(MainComponent.class).inject(this);
    }
}
