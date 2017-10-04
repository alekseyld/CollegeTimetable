package com.alekseyld.collegetimetable.view.adapter;

import android.support.annotation.NonNull;
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

    LessonAdapter(@NonNull Day day){
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

        //changes set background
//        HashMap<TimeTable.Day, HashMap<TimeTable.Lesson, Boolean>> changes = mTimeTable.getChanges();
//        if (changes != null)
//            for (TimeTable.Lesson l : changes.get(day).keySet()) {
//                if (changes.get(day).get(l)) {
//                    switch (l) {
//                        case lesson0:
//                            holder.lesson0.setBackgroundColor(Color.parseColor("#FFF176"));
//                            break;
//                        case lesson1:
//                            holder.lesson1.setBackgroundColor(Color.parseColor("#FFF176"));
//                            break;
//                        case lesson2:
//                            holder.lesson2.setBackgroundColor(Color.parseColor("#FFF176"));
//                            break;
//                        case lesson3:
//                            holder.lesson3.setBackgroundColor(Color.parseColor("#FFF176"));
//                            break;
//                        case lesson4:
//                            holder.lesson4.setBackgroundColor(Color.parseColor("#FFF176"));
//                            break;
//                        case lesson5:
//                            holder.lesson5.setBackgroundColor(Color.parseColor("#FFF176"));
//                            break;
//                        case lesson6:
//                            holder.lesson6.setBackgroundColor(Color.parseColor("#FFF176"));
//                            break;
//                    }
//                }
//            }

    }

    @Override
    public int getItemCount() {
        return lessonDay.getDayLessons().size();
    }

}
