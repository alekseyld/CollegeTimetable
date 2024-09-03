package com.alekseyld.collegetimetable.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.databinding.FragmentSettingsBinding;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.presenter.SettingsPresenter;
import com.alekseyld.collegetimetable.view.SettingsView;
import com.alekseyld.collegetimetable.view.activity.SettingsFavoriteActivity;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;
import com.alekseyld.collegetimetable.view.fragment.dialog.GroupInputDialogFragment;

/**
 * Created by Alekseyld on 04.09.2016.
 */
public class SettingsFragment extends BaseFragment<SettingsPresenter> implements SettingsView {

    private FragmentSettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        requireActivity().setTitle(R.string.action_settings);

        binding.addFarvorite.setOnClickListener(view -> showAddFavoriteDialog());

        binding.addTeacherGroup.setOnClickListener(v -> showAddTeacherDialog());

        binding.addNotif.setOnClickListener(view -> showAddNotif(
            mPresenter.getTeacherMode()
        ));

        binding.alarmMode.setOnClickListener(view -> mPresenter.saveAlarmMode(
            binding.alarmMode.isChecked()
        ));

        binding.notifOn.setOnClickListener(view -> {
            if (mPresenter.getNotificationGroup().isEmpty()) {
                showAlertDialog("Сначала необходимо выбрать мою группу!",
                    "Чтобы включить уведомления, необходимо сначала заполнить поле \"Моя группа\"", "Ок", "", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }, null);
                binding.notifOn.setChecked(!binding.notifOn.isChecked());
                return;
            }

            mPresenter.saveNotifOn(
                binding.notifOn.isChecked()
            );
        });

        binding.changeMode.setOnClickListener(v -> mPresenter.saveChangeMode(
            binding.changeMode.isChecked()
        ));

        binding.teacherMode.setOnClickListener(v -> showAlertDialog("Вы точно хотите перейти в режим преподавателя? ",
            "Все данные будут утеряны!",
            "Да",
            "Нет",
            (dialog, which) -> {
                mPresenter.saveTeacherMode(binding.teacherMode.isChecked());
                binding.addNotifTitle.startAnimation(getTeacherTitleAnimation());
            }, (dialog, which) -> {
                dialog.dismiss();
                binding.teacherMode.setChecked(!binding.teacherMode.isChecked());
            })
        );

        binding.darkMode.setOnClickListener(view -> {
            boolean isDarkMode = binding.darkMode.isChecked();

            mPresenter.saveDarkMode(isDarkMode);

            if (getActivity() != null) getActivity().recreate();
        });

        binding.updateLinks.setOnClickListener(view -> showAlertDialog("Обновить данные ссылке и группах?",
            "Чтобы посмотреть откуда и что будет обновляться, нажмите на \"Источник\" (откроется в браузере)",
            "Обновить",
            "Источник",
            (dialog, which) -> {
                mPresenter.updateLinks();
                dialog.dismiss();
                Toast.makeText(getContext(), "Идет обновление..", Toast.LENGTH_SHORT).show();
            }, (dialog, which) -> {
                startActivity(
                    new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://raw.githubusercontent.com/alekseyld/CollegeTimetable/master/docs/pref.html")
                    )
                );
                dialog.dismiss();
            }));

        TypedValue outValue = new TypedValue();
        requireContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        binding.addFarvorite.setBackgroundResource(outValue.resourceId);
        binding.addNotif.setBackgroundResource(outValue.resourceId);

        return binding.getRoot();
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
                if (binding.teacherMode.isChecked()) {
                    binding.addNotifTitle.setText(getString(R.string.teacherSettingTitle));
                    binding.myGroupValue.setVisibility(View.GONE);
                    binding.myGroupValue.setText("");
                    binding.addTeacherGroup.setVisibility(View.GONE);
                } else {
                    binding.addNotifTitle.setText(getString(R.string.mygroup));
                    binding.addTeacherGroup.setVisibility(View.VISIBLE);
                }

            }
        });
        return animation;
    }

    @Override
    public void presenterReady() {

        binding.alarmMode.setChecked(mPresenter.getAlarmMode());
        binding.notifOn.setChecked(mPresenter.getNotifOn());
        binding.changeMode.setChecked(mPresenter.getChangeMode());
        binding.teacherMode.setChecked(mPresenter.getTeacherMode());
        binding.darkMode.setChecked(mPresenter.getDarkMode());

        if (binding.teacherMode.isChecked()) {
            binding.addNotifTitle.setText(getString(R.string.teacherSettingTitle));
            binding.addTeacherGroup.setVisibility(View.VISIBLE);
        } else {
            binding.addNotifTitle.setText(getString(R.string.mygroup));
            binding.addTeacherGroup.setVisibility(View.GONE);
        }

        if (mPresenter.getNotificationGroup() != null && !mPresenter.getNotificationGroup().equals("")) {
            binding.myGroupValue.setVisibility(View.VISIBLE);
            binding.myGroupValue.setText(mPresenter.getNotificationGroup());
        } else {
            binding.myGroupValue.setVisibility(View.GONE);
        }
    }

    public void saveNotification(String group) {
        mPresenter.saveNotification(group);
    }

    private void showAddNotif(boolean teacherMode) {
        GroupInputDialogFragment groupInputDialogFragment = GroupInputDialogFragment.newInstance(false, teacherMode);
        groupInputDialogFragment.setTargetFragment(this, 1);
        groupInputDialogFragment.show(requireFragmentManager(), GroupInputDialogFragment.class.getSimpleName());
    }

    private void showAddFavoriteDialog() {
        getBaseActivity().startActivity(SettingsFavoriteActivity.class);
    }

    private void showAddTeacherDialog() {
        Intent intent = new Intent(getActivity(), SettingsFavoriteActivity.class);
        intent.putExtra("teacherMode", true);
        requireActivity().startActivity(intent);
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

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }
}
