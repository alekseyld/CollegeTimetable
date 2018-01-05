package com.alekseyld.collegetimetable.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.Notification;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.presenter.NotificationPresenter;
import com.alekseyld.collegetimetable.view.NotificationsView;
import com.alekseyld.collegetimetable.view.adapter.NotificationAdapter;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 04.01.2018.
 */

public class NotificationsFragment extends BaseFragment<NotificationPresenter> implements NotificationsView {

    public static NotificationsFragment newInstance(){
        return new NotificationsFragment();
    }

    @BindView(R.id.recView)
    RecyclerView notificationsRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private LinearLayoutManager mLayoutManager;
    private NotificationAdapter mNotificationAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle(R.string.notification_title);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        notificationsRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        notificationsRecyclerView.setLayoutManager(mLayoutManager);

        mNotificationAdapter = new NotificationAdapter();
        notificationsRecyclerView.setAdapter(mNotificationAdapter);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.getNotificationOffline();
    }

    private void refreshItems() {
        mPresenter.getNotifications();
        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setNotification(List<Notification> notification) {
        mNotificationAdapter.setNotifications(notification);
    }

    @Override
    public void addNotification(List<Notification> notification) {
        mNotificationAdapter.addNotifications(notification);
    }

    @Override
    public void showLoading() {
        notificationsRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        notificationsRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initializeInjections() {
        getComponent(MainComponent.class).inject(this);
    }
}
