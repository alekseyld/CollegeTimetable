package com.alekseyld.collegetimetable.view.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alekseyld.collegetimetable.databinding.ListTableBinding;

/**
 * Created by Alekseyld on 16.09.2017.
 */

public class TimeTableHolder extends RecyclerView.ViewHolder {

    public final TextView date;

    public final RecyclerView lessons;

    public final ImageView shareButton;

    public TimeTableHolder(View v) {
        super(v);
        ListTableBinding binding = ListTableBinding.bind(v);

        date = binding.date;
        lessons = binding.lessonList;
        shareButton = binding.shareButton;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        lessons.setLayoutManager(linearLayoutManager);
    }
}
