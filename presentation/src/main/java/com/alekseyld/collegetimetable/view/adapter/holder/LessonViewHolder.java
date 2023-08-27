package com.alekseyld.collegetimetable.view.adapter.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alekseyld.collegetimetable.databinding.ListTableLessonBinding;

/**
 * Created by Alekseyld on 16.09.2017.
 */

public class LessonViewHolder extends RecyclerView.ViewHolder {

    private final ListTableLessonBinding binding;

    public TextView lessonNumber;

    public TextView lessonTime;

    public TextView lessonName;

    public TextView lessonTeacher;

    public LessonViewHolder(View v) {
        super(v);
        binding = ListTableLessonBinding.bind(v);

        lessonNumber = binding.lessonNumber;
        lessonTime = binding.lessonTime;
        lessonName = binding.lessonName;
        lessonTeacher = binding.lessonTeacher;
    }
}
