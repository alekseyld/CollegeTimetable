package com.alekseyld.collegetimetable.view.adapter.base;

import android.os.Handler;
import androidx.recyclerview.widget.RecyclerView;

import com.alekseyld.collegetimetable.presenter.base.Presenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alekseyld on 10.09.2017.
 */

public abstract class BasePresenterAdapter<TItem, TPresenter extends Presenter, TViewHolder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<TViewHolder>{

    private List<TItem> mItems = new ArrayList<>();

    protected TPresenter mPresenter;

    public List<TItem> getItems() {
        return mItems;
    }

    public BasePresenterAdapter(TPresenter presenter) {
        mPresenter = presenter;
    }

    public void add(TItem item) {
        if (mItems.contains(item))
            mItems.set(mItems.indexOf(item), item);
        else
            mItems.add(item);
        notifyItemChanged(mItems.indexOf(item));
    }

    public void add(int position, TItem item) {
        mItems.add(position, item);
        notifyItemsChanged();
    }

    public void addAll(List<TItem> items) {
        mItems.addAll(items);
        notifyItemRangeChanged(mItems.size(), items.size());
    }

    public void remove(int position) {
        mItems.remove(position);
        notifyDataSetChanged();
    }

    public void remove(TItem item) {
        mItems.remove(item);
        notifyDataSetChanged();
    }

    public void removeAll() {
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public final int getItemCount() {
        return mItems.size();
    }

    public void notifyItemsChanged() {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                notifyDataSetChanged();
            }
        };
        handler.post(r);
    }

    public TPresenter getPresenter() {
        return mPresenter;
    }
}
