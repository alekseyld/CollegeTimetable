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

    private ListTableBinding binding;

    public TextView date;

    public RecyclerView lessons;

    public ImageView shareButton;

    public TimeTableHolder(View v) {
        super(v);
        binding = ListTableBinding.bind(v);

        date = binding.date;
        lessons = binding.lessonList;
        shareButton = binding.shareButton;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        lessons.setLayoutManager(linearLayoutManager);
    }
}
