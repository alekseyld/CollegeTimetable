package com.alekseyld.collegetimetable.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
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

    @BindView(R.id.teacherMode)
    Switch teachMode;

    @BindView(R.id.addFarvorite)
    TextView addFarvorite;

    @BindView(R.id.addNotif)
    LinearLayout addNotif;

    @BindView(R.id.addNotif_title)
    TextView addNotifTitle;

    @BindView(R.id.my_group_value)
    TextView addNotifValue;

    @BindView(R.id.alarmMode)
    Switch alarmMode;

    @BindView(R.id.notifOn)
    Switch notifOn;

    @BindView(R.id.changeMode)
    Switch changeMode;

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
                showAddNotif(
                        mPresenter.getTeacherMode()
                );
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

        teachMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSubmitDialog("Вы точно хотите перейти в режим преподавателя? Все данные будут утеряны.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.saveTeacherMode(teachMode.isChecked());
                        addNotifTitle.startAnimation(getTeacherTitleAnimation());
                    }
                });
            }
        });

        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        addFarvorite.setBackgroundResource(outValue.resourceId);
        addNotif.setBackgroundResource(outValue.resourceId);

        return v;
    }

    private Animation getTeacherTitleAnimation() {
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(200);
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (teachMode.isChecked()) {
                    addNotifTitle.setText(getString(R.string.teacherSettingTitle));
                    addNotifValue.setVisibility(View.GONE);
                    addNotifValue.setText("");
                } else {
                    addNotifTitle.setText(getString(R.string.mygroup));
                }

            }
        });
        return animation;
    }

    @Override
    public void presenterReady() {

        alarmMode.setChecked(mPresenter.getAlarmMode());
        notifOn.setChecked(mPresenter.getNotifOn());
        changeMode.setChecked(mPresenter.getChangeMode());
        teachMode.setChecked(mPresenter.getTeacherMode());

        if (teachMode.isChecked()) {
            addNotifTitle.setText(getString(R.string.teacherSettingTitle));
        } else {
            addNotifTitle.setText(getString(R.string.mygroup));
        }

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

    private void showAddNotif(boolean teacherMode){
        GroupInputDialogFragment groupInputDialogFragment = GroupInputDialogFragment.newInstance(false, teacherMode);
        groupInputDialogFragment.setTargetFragment(this, 1);
        groupInputDialogFragment.show(getFragmentManager(), GroupInputDialogFragment.class.getSimpleName());
    }

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
