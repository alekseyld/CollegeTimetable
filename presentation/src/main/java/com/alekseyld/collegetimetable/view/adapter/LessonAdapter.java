package com.alekseyld.collegetimetable.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.view.adapter.holder.LessonViewHolder;

import java.util.HashMap;

/**
 * Created by Alekseyld on 16.09.2017.
 */

public class LessonAdapter extends RecyclerView.Adapter<LessonViewHolder> {

    //// FIXME: 16.09.2017 to lesson array list
    private HashMap<TimeTable.Day, HashMap<TimeTable.Lesson, String>> mLessons;
    private TimeTable.Day mDay;

    public LessonAdapter(@NonNull HashMap<TimeTable.Day, HashMap<TimeTable.Lesson, String>> lessons, TimeTable.Day day){
        mLessons = lessons;
        mDay = day;
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

        switch (position){
            case 0:
                holder.lessonNumber.setText("0.");
                holder.lessonName.setText(mLessons.get(mDay).get(TimeTable.Lesson.lesson0));
                holder.lessonTeacher.setVisibility(View.GONE);
                break;
            case 1:
                holder.lessonNumber.setText("1.");
                holder.lessonName.setText(mLessons.get(mDay).get(TimeTable.Lesson.lesson1));
                holder.lessonTeacher.setVisibility(View.GONE);
                break;
            case 2:
                holder.lessonNumber.setText("2.");
                holder.lessonName.setText(mLessons.get(mDay).get(TimeTable.Lesson.lesson2));
                holder.lessonTeacher.setVisibility(View.GONE);
                break;
            case 3:
                holder.lessonNumber.setText("3.");
                holder.lessonName.setText(mLessons.get(mDay).get(TimeTable.Lesson.lesson3));
                holder.lessonTeacher.setVisibility(View.GONE);
                break;
            case 4:
                holder.lessonNumber.setText("4.");
                holder.lessonName.setText(mLessons.get(mDay).get(TimeTable.Lesson.lesson4));
                holder.lessonTeacher.setVisibility(View.GONE);
                break;
            case 5:
                holder.lessonNumber.setText("5.");
                holder.lessonName.setText(mLessons.get(mDay).get(TimeTable.Lesson.lesson5));
                holder.lessonTeacher.setVisibility(View.GONE);
                break;
            case 6:
                holder.lessonNumber.setText("6.");
                holder.lessonName.setText(mLessons.get(mDay).get(TimeTable.Lesson.lesson6));
                holder.lessonTeacher.setVisibility(View.GONE);
                break;
        }

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
        //// FIXME: 16.09.2017 lenght
        return 7;
    }

}
