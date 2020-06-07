package com.alekseyld.collegetimetable.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.view.TableView;
import com.alekseyld.collegetimetable.view.adapter.holder.TimeTableHolder;

import java.util.Objects;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TableAdapter extends RecyclerView.Adapter<TimeTableHolder> {

    private TimeTable mTimeTable;
    private Context context;

    private TableView mView;
    private RecyclerView.LayoutManager layoutManager;

    public TableAdapter(Context context, RecyclerView.LayoutManager layoutManager, TableView view) {
        this.context = context;
        mTimeTable = null;
        this.layoutManager = layoutManager;
        this.mView = view;
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
        holder.lessons.setAdapter(new LessonAdapter(mTimeTable.getDayList().get(position), mView.getChangeMode(), context));

        if (layoutManager != null) {
            holder.shareButton.setVisibility(View.VISIBLE);

            holder.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mView == null) return;
                    mView.shareDay(getDayByBitmap(holder.getAdapterPosition()));
                }
            });

            holder.date.setPadding(
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 34, context.getResources().getDisplayMetrics()),
                    holder.date.getPaddingTop(),
                    holder.date.getPaddingRight(),
                    holder.date.getPaddingBottom()
            );
        }
    }

    private Bitmap getDayByBitmap(int pos) {
//        View view = layoutManager.findViewByPosition(pos);
//        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache();
//
//        return view.getDrawingCache();


        View view = layoutManager.findViewByPosition(pos);
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas bitmapHolder = new Canvas(bitmap);
        bitmapHolder.drawRGB(245, 245, 245);

        view.draw(bitmapHolder);

        return bitmap;
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
