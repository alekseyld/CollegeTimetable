package com.alekseyld.collegetimetable.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.TableWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder>{

    private TableWrapper mTableWrapper;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private View mView;

        @BindView(R.id.date) public TextView date;
        @BindView(R.id.lesson0) public TextView lesson0;
        @BindView(R.id.lesson1) public TextView lesson1;
        @BindView(R.id.lesson2) public TextView lesson2;
        @BindView(R.id.lesson3) public TextView lesson3;
        @BindView(R.id.lesson4) public TextView lesson4;
        @BindView(R.id.lesson5) public TextView lesson5;
        @BindView(R.id.lesson6) public TextView lesson6;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            ButterKnife.bind(this, v);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TableAdapter() {
        mTableWrapper = null;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_table, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return  new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if(mTableWrapper != null) {
            TableWrapper.Day day = TableWrapper.Day.Mon;
            String dayText = "";

            switch (position) {
                case 0:
                    day = TableWrapper.Day.Mon;
//                    dayText = "Понедельник";
                    break;
                case 1:
                    day = TableWrapper.Day.Tue;
//                    dayText = "Вторник";
                    break;
                case 2:
                    day = TableWrapper.Day.Wed;
//                    dayText = "Среда";
                    break;
                case 3:
                    day = TableWrapper.Day.Thu;
//                    dayText = "Четверг";
                    break;
                case 4:
                    day = TableWrapper.Day.Friday;
//                    dayText = "Пятница";
                    break;
                case 5:
                    day = TableWrapper.Day.Saturday;
//                    dayText = "Суббота";
                    break;
                case 6:
                    day = TableWrapper.Day.Mon2;
//                    dayText = "Понедельник";
                    break;
            }

//            holder.date.setText(dayText);

            holder.date.setText(mTableWrapper.getDays().get(day));
            holder.lesson0.setText(mTableWrapper.getmTimeTable().get(day).get(TableWrapper.Lesson.lesson0));
            holder.lesson1.setText(mTableWrapper.getmTimeTable().get(day).get(TableWrapper.Lesson.lesson1));
            holder.lesson2.setText(mTableWrapper.getmTimeTable().get(day).get(TableWrapper.Lesson.lesson2));
            holder.lesson3.setText(mTableWrapper.getmTimeTable().get(day).get(TableWrapper.Lesson.lesson3));
            holder.lesson4.setText(mTableWrapper.getmTimeTable().get(day).get(TableWrapper.Lesson.lesson4));
            holder.lesson5.setText(mTableWrapper.getmTimeTable().get(day).get(TableWrapper.Lesson.lesson5));
            holder.lesson6.setText(mTableWrapper.getmTimeTable().get(day).get(TableWrapper.Lesson.lesson6));
        }
    }

    public void setTableWrapper(TableWrapper tableWrapper){
        mTableWrapper = tableWrapper;
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(mTableWrapper == null) {
            return 0;
        }
        return mTableWrapper.getmTimeTable().size();
    }
}
