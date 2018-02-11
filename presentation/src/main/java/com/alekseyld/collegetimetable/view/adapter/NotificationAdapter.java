package com.alekseyld.collegetimetable.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.Notification;
import com.alekseyld.collegetimetable.utils.Utils;
import com.alekseyld.collegetimetable.view.adapter.holder.NotificationHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Alekseyld on 05.01.2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationHolder> implements Filterable {

    private List<Notification> notificationList;

    private List<Notification> notificationListFiltered;

    public NotificationAdapter() {
        notificationList = new ArrayList<>();
        notificationListFiltered = new ArrayList<>();
    }

    @Override
    public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_notification, parent, false);
        return new NotificationHolder(v);
    }

    @Override
    public void onBindViewHolder(NotificationHolder holder, int position) {

        Notification notification = notificationListFiltered.get(holder.getAdapterPosition());

        holder.title.setText(notification.getTitle());
        holder.text.setText(notification.getText());
        holder.author.setText(notification.getAuthor());
        holder.date.setText(Utils.dateFormat.format(notification.getDate()));

    }

    public void setNotifications(List<Notification> items) {
        if (items == null)
            return;
        this.notificationList = sortNotificationByDate(items);
        this.notificationListFiltered = notificationList;
    }

    public void addNotifications(List<Notification> newItems) {
        //todo sort by date

        this.notificationList.addAll(sortNotificationByDate(newItems));
        this.notificationListFiltered.addAll(notificationList);
    }

    @Override
    public int getItemCount() {
        return notificationListFiltered.size();
    }

    private List<Notification> sortNotificationByDate(List<Notification> notifications) {
        Collections.sort(notifications, new Comparator<Notification>() {
            public int compare(Notification n1, Notification n2) {
                return n2.getDate().compareTo(n1.getDate());
            }
        });

        return notifications;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    notificationListFiltered = notificationList;
                } else {
                    List<Notification> filteredList = new ArrayList<>();
                    for (Notification notification : notificationList) {

                        if (notification.getTitle().toLowerCase().contains(charString.toLowerCase())
                                || notification.getText().contains(charSequence)
                                || notification.getAuthor().contains(charSequence)
                                || Utils.dateFormat.format(notification.getDate()).contains(charSequence)) {
                            filteredList.add(notification);
                        }
                    }

                    notificationListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = notificationListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notificationListFiltered = (ArrayList<Notification>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
