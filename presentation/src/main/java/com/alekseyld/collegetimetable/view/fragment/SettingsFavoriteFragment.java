package com.alekseyld.collegetimetable.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.SettingsFavoriteComponent;
import com.alekseyld.collegetimetable.presenter.SettingsFavoritePresenter;
import com.alekseyld.collegetimetable.view.SettingsFavoriteView;
import com.alekseyld.collegetimetable.view.adapter.FavoriteGroupAdapter;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;
import com.alekseyld.collegetimetable.view.fragment.dialog.GroupInputDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alekseyld on 07.09.2017.
 */

public class SettingsFavoriteFragment extends BaseFragment<SettingsFavoritePresenter> implements SettingsFavoriteView {

    public static SettingsFavoriteFragment newInstance(boolean teacherMode){
        Bundle bundle = new Bundle();
        bundle.putBoolean("teacherMode", teacherMode);
        SettingsFavoriteFragment favoriteFragment = new SettingsFavoriteFragment();
        favoriteFragment.setArguments(bundle);
        return favoriteFragment;
    }

    @BindView(R.id.listGroup)
    RecyclerView listGroup;

    @BindView(R.id.error_message)
    TextView message;

    private FavoriteGroupAdapter mAdapter;

    private boolean teacherMode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle(R.string.settings_favorite_activity_title);

        teacherMode = getArguments().getBoolean("teacherMode", false);

        return v;
    }

    @Override
    public void onResume() {
        if (mAdapter == null){
            mAdapter = new FavoriteGroupAdapter(mPresenter, getContext(), teacherMode);
            listGroup.setAdapter(mAdapter);
            listGroup.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        message.setVisibility(View.VISIBLE);
        message.setText(mes);
    }

    @OnClick(R.id.fab)
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
        message.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        message.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    protected void initializeInjections() {
        getComponent(SettingsFavoriteComponent.class).inject(this);
    }
}
