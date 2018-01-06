package com.alekseyld.collegetimetable.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.LoginComponent;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.presenter.SettingsPresenter;
import com.alekseyld.collegetimetable.view.SettingsView;
import com.alekseyld.collegetimetable.view.activity.SettingsFavoriteActivity;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;
import com.alekseyld.collegetimetable.view.fragment.dialog.GroupInputDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 04.09.2017.
 */

public class SettingsFragment extends BaseFragment<SettingsPresenter> implements SettingsView {

    public static SettingsFragment newInstance(boolean isLogin){
        SettingsFragment fragment = new SettingsFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isLogin", isLogin);
        fragment.setArguments(bundle);
        return fragment;
    }


    @BindView(R.id.setUrlServer)
    TextView setUrlServer;

    @BindView(R.id.addFarvorite)
    TextView addFarvorite;

    @BindView(R.id.addNotif)
    LinearLayout addNotif;

    @BindView(R.id.my_group_value)
    TextView addNotifValue;

    @BindView(R.id.alarmMode)
    Switch alarmMode;

    @BindView(R.id.notifOn)
    Switch notifOn;

    @BindView(R.id.changeMode)
    Switch changeMode;

    boolean isLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle(R.string.action_settings);

        setUrlServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUrlDialog();
            }
        });

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

        changeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveChangeMode(
                        changeMode.isChecked()
                );
            }
        });

        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        addFarvorite.setBackgroundResource(outValue.resourceId);
        addNotif.setBackgroundResource(outValue.resourceId);

        if (getArguments() != null)
            isLogin = getArguments().getBoolean("isLogin");

        return v;
    }

    @Override
    public void presenterReady() {

        alarmMode.setChecked(mPresenter.getAlarmMode());
        notifOn.setChecked(mPresenter.getNotifOn());
        changeMode.setChecked(mPresenter.getChangeMode());

        if (mPresenter.getNotificationGroup() != null && !mPresenter.getNotificationGroup().equals("")) {
            addNotifValue.setVisibility(View.VISIBLE);
            addNotifValue.setText(mPresenter.getNotificationGroup());
        } else {
            addNotifValue.setVisibility(View.GONE);
        }
    }

    public void saveNotification(String group){
        mPresenter.saveNotification(group);
    }

    private void showAddNotif(){
        GroupInputDialogFragment groupInputDialogFragment = GroupInputDialogFragment.newInstance(false);
        groupInputDialogFragment.setTargetFragment(this, 1);
        groupInputDialogFragment.show(getFragmentManager(), GroupInputDialogFragment.class.getSimpleName());
    }

    private void showAddFavoriteDialog(){
        getBaseActivity().startActivity(SettingsFavoriteActivity.class);
    }

    private void showUrlDialog() {
        //todo переделать на material edit text
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        final EditText input = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setHint("Адрес сервера");
        if (mPresenter.getSettings().getUrlServer() != null)
            input.setText(mPresenter.getSettings().getUrlServer());
        alertDialog.setView(input)
                .setTitle("")
                .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.saveUrlServer(input.getText().toString());
                    }
                })
                .show();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    protected void initializeInjections() {
        if (isLogin) {
            getComponent(LoginComponent.class).inject(this);
        } else {
            getComponent(MainComponent.class).inject(this);
        }
    }

}
