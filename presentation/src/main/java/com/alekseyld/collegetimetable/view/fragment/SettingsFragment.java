package com.alekseyld.collegetimetable.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.presenter.SettingsPresenter;
import com.alekseyld.collegetimetable.view.SettingsView;
import com.alekseyld.collegetimetable.view.activity.SettingsFavoriteActivity;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;
import com.alekseyld.collegetimetable.view.fragment.dialog.GroupInputDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class SettingsFragment extends BaseFragment<SettingsPresenter> implements SettingsView {

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @BindView(R.id.addFarvorite)
    TextView addFarvorite;

    @BindView(R.id.addNotif)
    TextView addNotif;

    @BindView(R.id.alarmMode)
    Switch alarmMode;

    @BindView(R.id.notifOn)
    Switch notifOn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle(R.string.action_settings);

        addFarvorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddFavoriteDialog();
            }
        });

        addNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNotif();
            }
        });

        alarmMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.saveAlarmMode(
                        alarmMode.isChecked()
                );
            }
        });

        notifOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.saveNotifOn(
                        notifOn.isChecked()
                );
            }
        });

        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        addFarvorite.setBackgroundResource(outValue.resourceId);
        addNotif.setBackgroundResource(outValue.resourceId);

        return v;
    }

    @Override
    public void presenterReady() {
        alarmMode.setChecked(
                mPresenter.getAlarmMode()
        );

        notifOn.setChecked(
                mPresenter.getNotifOn()
        );
    }

    public void saveNotification(String group){
        mPresenter.saveNotification(group);
    }

    private void showAddNotif(){
        GroupInputDialogFragment groupInputDialogFragment = GroupInputDialogFragment.newInstance(false);
        groupInputDialogFragment.setTargetFragment(this, 1);
        groupInputDialogFragment.show(getFragmentManager(), GroupInputDialogFragment.class.getSimpleName());
    }

    // TODO: 10.09.2017  navigator maybe
    private void showAddFavoriteDialog(){
        getBaseActivity().startActivity(SettingsFavoriteActivity.class);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void initializeInjections() {
        getComponent(MainComponent.class).inject(this);
    }

}
