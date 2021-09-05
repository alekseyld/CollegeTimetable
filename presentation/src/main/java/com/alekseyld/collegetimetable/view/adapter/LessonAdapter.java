package com.alekseyld.collegetimetable.view.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.Day;
import com.alekseyld.collegetimetable.entity.Lesson;
import com.alekseyld.collegetimetable.view.activity.base.BaseActivity;
import com.alekseyld.collegetimetable.view.adapter.holder.LessonViewHolder;

/**
 * Created by Alekseyld on 16.09.2017.
 */

class LessonAdapter extends RecyclerView.Adapter<LessonViewHolder> {

    private Day lessonDay;
    private Context context;

    private boolean isChangeMode;

    LessonAdapter(@NonNull Day day, boolean isChangeMode, Context context) {
        this.context = context;
        lessonDay = day;
        this.isChangeMode = isChangeMode;
    }

    @Override
    public LessonViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_table_lesson, parent, false);

        return new LessonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LessonViewHolder holder, int position) {

        Lesson lesson = lessonDay.getDayLessons().get(position);

        holder.lessonNumber.setText(lesson.getNumber() + ".");
        holder.lessonName.setText(lesson.getDoubleName());
        holder.lessonTeacher.setVisibility(View.GONE);

        if (lesson.getTime() != null && !lesson.getTime().isEmpty()) {
            holder.lessonTime.setText(lesson.getTime());
            holder.lessonTime.setVisibility(View.VISIBLE);
        } else {
            holder.lessonTime.setVisibility(View.GONE);
        }

        if (position == getItemCount() - 1) {
            holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.border_background));

        } else {
            TypedArray ta = context.getTheme().obtainStyledAttributes(R.styleable.AppTheme);

            int dividerColor = ta.getColor(R.styleable.AppTheme_listBackground, 0);

            holder.itemView.setBackgroundColor(dividerColor);
        }

        if (lesson.isChange()) {
            if (isChangeMode) {

                if (BaseActivity.isDarkMode) {
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.darkColorChanges));
                } else {
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorChanges));
                }


            } else {
                holder.lessonName.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
            }
        }


    }

    @Override
    public int getItemCount() {
        return lessonDay.getDayLessons().size();
    }

}
