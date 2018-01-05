package com.alekseyld.collegetimetable.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.Notification;
import com.alekseyld.collegetimetable.view.adapter.holder.NotificationHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alekseyld on 05.01.2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationHolder> {

    private List<Notification> mItems;

    public NotificationAdapter() {
        mItems = new ArrayList<>();
    }

    @Override
    public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_notification, parent, false);
        return new NotificationHolder(v);
    }

    @Override
    public void onBindViewHolder(NotificationHolder holder, int position) {

        Notification notification = mItems.get(holder.getAdapterPosition());

        holder.title.setText(notification.getTitle());
        holder.text.setText(notification.getText());
        holder.author.setText(notification.getAuthor());
        holder.date.setText(notification.getDate().toString());

    }

    public void setNotifications(List<Notification> items) {
        if (items == null)
            return;

        this.mItems = items;
    }

    public void addNotifications(List<Notification> newItems) {
        //todo sort by date
        this.mItems.addAll(newItems);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
