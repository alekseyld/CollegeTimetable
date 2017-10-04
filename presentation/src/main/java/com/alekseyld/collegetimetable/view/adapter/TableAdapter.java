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

    public TableAdapter() {
        mTimeTable = null;
    }

    public TableAdapter(TimeTable timeTable) {
        mTimeTable = timeTable;
    }

    @Override
    public TimeTableHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_table, parent, false);
        return new TimeTableHolder(v);
    }

    @Override
    public void onBindViewHolder(TimeTableHolder holder, int position) {
        holder.date.setText(mTimeTable.getDayList().get(position).getDateFirstUpperCase());
        holder.lessons.setAdapter(new LessonAdapter(mTimeTable.getDayList().get(position)));
    }

    public TimeTable getTimeTable() {
        return mTimeTable;
    }

    public void setTimeTable(TimeTable timeTable) {
        mTimeTable = timeTable;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTimeTable == null || mTimeTable.getDayList() == null) {
            return 0;
        }
        return mTimeTable.getDayList().size();
    }
}
