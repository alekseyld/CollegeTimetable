package com.alekseyld.collegetimetable.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.view.adapter.holder.TimeTableHolder;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TableAdapter extends RecyclerView.Adapter<TimeTableHolder> {

    private TimeTable mTimeTable;

    // Provide a suitable constructor (depends on the kind of dataset)
    public TableAdapter() {
        mTimeTable = null;
    }

    public TableAdapter(TimeTable timeTable) {
        mTimeTable = timeTable;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TimeTableHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_table, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new TimeTableHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TimeTableHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.date.setText(firstUpperCase(mTimeTable.getDayList().get(position).getDate().toLowerCase()));
        holder.lessons.setAdapter(new LessonAdapter(mTimeTable.getDayList().get(position)));
    }

    public TimeTable getTimeTable() {
        return mTimeTable;
    }

    public void setTimeTable(TimeTable timeTable) {
        mTimeTable = timeTable;
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mTimeTable == null || mTimeTable.getDayList() == null) {
            return 0;
        }
        return mTimeTable.getDayList().size();
    }

    private String firstUpperCase(String word) {
        if (word == null || word.isEmpty()) return "";//или return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
