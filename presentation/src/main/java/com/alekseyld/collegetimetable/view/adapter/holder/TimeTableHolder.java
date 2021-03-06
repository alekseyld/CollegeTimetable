package com.alekseyld.collegetimetable.view.adapter.holder;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alekseyld.collegetimetable.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 16.09.2017.
 */

public class TimeTableHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.date)
    public TextView date;

    @BindView(R.id.lesson_list)
    public RecyclerView lessons;

    @BindView(R.id.share_button)
    public ImageView shareButton;

    public TimeTableHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        lessons.setLayoutManager(linearLayoutManager);
    }
}
