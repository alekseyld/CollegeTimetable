package com.alekseyld.collegetimetable.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.Day;
import com.alekseyld.collegetimetable.entity.Lesson;
import com.alekseyld.collegetimetable.view.adapter.holder.LessonViewHolder;

/**
 * Created by Alekseyld on 16.09.2017.
 */

class LessonAdapter extends RecyclerView.Adapter<LessonViewHolder> {

    private Day lessonDay;
    private Context context;

    LessonAdapter(@NonNull Day day, Context context){
        this.context = context;
        lessonDay = day;
    }

    @Override
    public LessonViewHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_table_lesson, parent, false);

        return  new LessonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LessonViewHolder holder, int position) {

        Lesson lesson = lessonDay.getDayLessons().get(position);

        holder.lessonNumber.setText(lesson.getNumber() + ".");
        holder.lessonName.setText(lesson.getDoubleName());
        holder.lessonTeacher.setVisibility(View.GONE);

        if (lesson.isChange()) {
            holder.lessonName.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
        }

    }

    @Override
    public int getItemCount() {
        return lessonDay.getDayLessons().size();
    }

}
