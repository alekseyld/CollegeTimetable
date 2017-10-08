package com.alekseyld.collegetimetable.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.presenter.TablePresenter;
import com.alekseyld.collegetimetable.view.adapter.holder.TimeTableHolder;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TableAdapter extends RecyclerView.Adapter<TimeTableHolder> {

    private TimeTable mTimeTable;
    private Context context;

    private TablePresenter mPresenter;
    private RecyclerView.LayoutManager layoutManager;

    public TableAdapter(Context context, RecyclerView.LayoutManager layoutManager) {
        this.context = context;
        mTimeTable = null;
        this.layoutManager = layoutManager;
    }

    public TableAdapter(Context context, TimeTable timeTable) {
        this.context = context;
        mTimeTable = timeTable;
    }

    public TableAdapter setPresenter(TablePresenter presenter) {
        this.mPresenter = presenter;
        return this;
    }

    @Override
    public TimeTableHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_table, parent, false);
        return new TimeTableHolder(v);
    }

    @Override
    public void onBindViewHolder(final TimeTableHolder holder, int position) {
        holder.date.setText(mTimeTable.getDayList().get(position).getDateFirstUpperCase());
        holder.lessons.setAdapter(new LessonAdapter(mTimeTable.getDayList().get(position), mPresenter.getChangeMode(), context));

        if (layoutManager != null) {
            holder.shareButton.setVisibility(View.VISIBLE);

            holder.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.shareDay(getDayByBitmap(holder.getAdapterPosition()));
                }
            });
        }
    }

    private Bitmap getDayByBitmap(int pos){
        View view = layoutManager.findViewByPosition(pos);
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        return view.getDrawingCache();
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
