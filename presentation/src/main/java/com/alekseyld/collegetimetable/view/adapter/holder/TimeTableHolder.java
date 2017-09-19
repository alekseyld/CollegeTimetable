package com.alekseyld.collegetimetable.view.adapter.holder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

//    @BindView(R.id.lesson0) public TextView lesson0;
//    @BindView(R.id.lesson1) public TextView lesson1;
//    @BindView(R.id.lesson2) public TextView lesson2;
//    @BindView(R.id.lesson3) public TextView lesson3;
//    @BindView(R.id.lesson4) public TextView lesson4;
//    @BindView(R.id.lesson5) public TextView lesson5;
//    @BindView(R.id.lesson6) public TextView lesson6;

    @BindView(R.id.lesson_list)
    public RecyclerView lessons;

    public TimeTableHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        lessons.setLayoutManager(linearLayoutManager);
    }
}
