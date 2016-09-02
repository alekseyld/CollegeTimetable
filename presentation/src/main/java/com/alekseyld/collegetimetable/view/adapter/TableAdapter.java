package com.alekseyld.collegetimetable.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alekseyld.collegetimetable.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder>{
    private String[] mDataset;

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
            ButterKnife.bind(v);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TableAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_table, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.date.setText(mDataset[position]);
        holder.lesson0.setText(mDataset[position]);
        holder.lesson1.setText(mDataset[position]);
        holder.lesson2.setText(mDataset[position]);
        holder.lesson3.setText(mDataset[position]);
        holder.lesson4.setText(mDataset[position]);
        holder.lesson5.setText(mDataset[position]);
        holder.lesson6.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
