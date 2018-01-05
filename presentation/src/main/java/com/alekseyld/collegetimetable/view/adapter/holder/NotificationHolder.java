package com.alekseyld.collegetimetable.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alekseyld.collegetimetable.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 05.01.2018.
 */

public class NotificationHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title)
    public TextView title;

    @BindView(R.id.text)
    public TextView text;

    @BindView(R.id.author)
    public TextView author;

    @BindView(R.id.date)
    public TextView date;

    public NotificationHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);

    }

}
