package com.alekseyld.collegetimetable.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.databinding.FragmentFavoriteBinding;
import com.alekseyld.collegetimetable.internal.di.component.SettingsFavoriteComponent;
import com.alekseyld.collegetimetable.presenter.SettingsFavoritePresenter;
import com.alekseyld.collegetimetable.view.SettingsFavoriteView;
import com.alekseyld.collegetimetable.view.adapter.FavoriteGroupAdapter;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;
import com.alekseyld.collegetimetable.view.fragment.dialog.GroupInputDialogFragment;

/**
 * Created by Alekseyld on 07.09.2017.
 */

public class SettingsFavoriteFragment extends BaseFragment<SettingsFavoritePresenter> implements SettingsFavoriteView {

    private FragmentFavoriteBinding binding;

    private FavoriteGroupAdapter mAdapter;

    private boolean teacherMode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        getActivity().setTitle(R.string.settings_favorite_activity_title);

        teacherMode = getArguments().getBoolean("teacherMode", false);

        binding.fab.setOnClickListener(v -> onFabClick());

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        if (mAdapter == null){
            mAdapter = new FavoriteGroupAdapter(mPresenter, getContext(), teacherMode);
            binding.listGroup.setAdapter(mAdapter);
            binding.listGroup.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        super.onResume();
    }

    @Override
    public boolean getTeacherMode() {
        return teacherMode;
    }

    @Override
    public FavoriteGroupAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setMessage(String mes) {
        binding.errorMessage.setVisibility(View.VISIBLE);
        binding.errorMessage.setText(mes);
    }

    void onFabClick(){
        GroupInputDialogFragment groupInputDialogFragment = GroupInputDialogFragment.newInstance(true, false);
        groupInputDialogFragment.setTargetFragment(this, 2);
        groupInputDialogFragment.show(getFragmentManager(), GroupInputDialogFragment.class.getSimpleName());
    }

    public void addFavoriteGroup(String group){
        mPresenter.addFavoriteGroup(group, teacherMode);
    }

    @Override
    public void showLoading() {
        binding.errorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.errorMessage.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    protected void initializeInjections() {
        getComponent(SettingsFavoriteComponent.class).inject(this);
    }

    public static SettingsFavoriteFragment newInstance(boolean teacherMode){
        Bundle bundle = new Bundle();
        bundle.putBoolean("teacherMode", teacherMode);
        SettingsFavoriteFragment favoriteFragment = new SettingsFavoriteFragment();
        favoriteFragment.setArguments(bundle);
        return favoriteFragment;
    }
}
