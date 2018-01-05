package com.alekseyld.collegetimetable.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.presenter.CertificatePresenter;
import com.alekseyld.collegetimetable.view.CertificateView;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 04.01.2018.
 */

public class CertificateFragment extends BaseFragment<CertificatePresenter> implements CertificateView {

    public static CertificateFragment newInstance(){
        return new CertificateFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_certificate, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle(R.string.certificate_title);

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
