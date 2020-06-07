package com.alekseyld.collegetimetable.view.adapter.holder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alekseyld.collegetimetable.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 16.09.2017.
 */

public class LessonViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.lesson_number)
    public TextView lessonNumber;

    @BindView(R.id.lesson_name)
    public TextView lessonName;

    @BindView(R.id.lesson_teacher)
    public TextView lessonTeacher;

    public LessonViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }
}
