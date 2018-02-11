package com.alekseyld.collegetimetable.view.fragment;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.User;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.presenter.CertificatePresenter;
import com.alekseyld.collegetimetable.view.CertificateView;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alekseyld on 04.01.2018.
 */

public class CertificateFragment extends BaseFragment<CertificatePresenter> implements CertificateView {

    public static CertificateFragment newInstance() {
        return new CertificateFragment();
    }

    @BindView(R.id.buttons)
    LinearLayout buttonsLinerLayout;

    @BindView(R.id.info)
    LinearLayout infoLinerLayout;

    @BindView(R.id.button_certificate)
    Button buttonCertificate;

    @BindView(R.id.button_certificatevoenkomat)
    Button buttonCertificateVoenkomat;

    @BindView(R.id.send_button)
    Button sendButton;

    @BindView(R.id.type_spinner)
    AppCompatSpinner typeSpinner;

    @BindView(R.id.count_spinner)
    AppCompatSpinner countSpinner;

    @BindView(R.id.fio_ed)
    MaterialEditText fio;

    @BindView(R.id.group_ed)
    MaterialEditText group;

    @BindView(R.id.studentid_ed)
    MaterialEditText studentid;

    @BindView(R.id.district_ed)
    MaterialEditText district;

    String[] spinnerMenu = {"Справка об обучении", "Справка в военкомат"};
    String[] spinnerCountMenu = {"2", "4"};

    ViewGroup containerViewGroup;

    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_certificate, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle(R.string.certificate_title);

        containerViewGroup = container;

        buttonCertificate.getBackground().setColorFilter(0xFF4CAF50, PorterDuff.Mode.MULTIPLY);
        buttonCertificateVoenkomat.getBackground().setColorFilter(0xFF4CAF50, PorterDuff.Mode.MULTIPLY);
        sendButton.getBackground().setColorFilter(0xFF4CAF50, PorterDuff.Mode.MULTIPLY);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerMenu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSpinner.setAdapter(adapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        district.setVisibility(View.GONE);
                        break;
                    case 1:
                        district.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerCountMenu);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        countSpinner.setAdapter(adapter2);

        return v;
    }

    @Override
    public void setUserInfo(User user) {

        fio.setText(user.getFullName());
        group.setText(user.getGroup());
        studentid.setText(user.getStudentId());
    }

    @OnClick(R.id.button_certificate)
    void clickCertificate() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(containerViewGroup);
        }

        buttonsLinerLayout.setVisibility(View.GONE);
        infoLinerLayout.setVisibility(View.VISIBLE);
        typeSpinner.setSelection(0);
        district.setVisibility(View.GONE);

    }

    @OnClick(R.id.button_certificatevoenkomat)
    void clickCertificateVoenkomat() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(containerViewGroup);
        }

        buttonsLinerLayout.setVisibility(View.GONE);
        infoLinerLayout.setVisibility(View.VISIBLE);
        typeSpinner.setSelection(1);
        district.setVisibility(View.VISIBLE);

    }

    private boolean checkEditTexts() {
        boolean error = false;

        if (fio.getText().toString().equals("")) {
            fio.setError("Необходимо заполнить обязательное поле");
            error = true;
        }

        if (group.getText().toString().equals("")) {
            group.setError("Необходимо заполнить обязательное поле");
            error = true;
        }

        if (studentid.getText().toString().equals("")) {
            studentid.setError("Необходимо заполнить обязательное поле");
            error = true;
        }

        if (district.getVisibility() != View.GONE && district.getText().toString().equals("")) {
            district.setError("Необходимо заполнить обязательное поле");
            error = true;
        }

        return error;
    }

    @OnClick(R.id.send_button)
    void clickSend() {

        if (checkEditTexts())
            return;

        mPresenter.sendCertificate(
                typeSpinner.getSelectedItemPosition(),
                countSpinner.getSelectedItemPosition(),
                fio.getText().toString(),
                group.getText().toString(),
                studentid.getText().toString(),
                district.getText().toString()
        );
    }

    @Override
    public void back() {
        getActivity().onBackPressed();
    }

    @Override
    public void showLoading() {
        mProgressDialog = ProgressDialog.show(getContext(), "",
                "Идет загрузка...", true);
    }

    @Override
    public void hideLoading() {
        mProgressDialog.hide();
    }

    @Override
    protected void initializeInjections() {
        getComponent(MainComponent.class).inject(this);
    }
}
