package com.alekseyld.collegetimetable.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.presenter.SettingsFavoritePresenter;
import com.alekseyld.collegetimetable.view.adapter.base.BasePresenterAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 10.09.2017.
 */

public class FavoriteGroupAdapter extends BasePresenterAdapter<String, SettingsFavoritePresenter, FavoriteGroupAdapter.FavoriteGroupViewHolder> {

    public FavoriteGroupAdapter(SettingsFavoritePresenter presenter) {
        super(presenter);
    }

    public static class FavoriteGroupViewHolder extends RecyclerView.ViewHolder {
        private View mView;

        @BindView(R.id.group_name)
        TextView groupName;

        @BindView(R.id.delete)
        ImageView delete;

        public FavoriteGroupViewHolder(View v) {
            super(v);
            mView = v;
            ButterKnife.bind(this, v);
        }
    }


    @Override
    public FavoriteGroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_favorite_group, parent, false);

        return  new FavoriteGroupAdapter.FavoriteGroupViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FavoriteGroupViewHolder holder, int position) {
        holder.groupName.setText(getItems().get(holder.getAdapterPosition()));

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.removeFavoriteGroup(getItems().get(holder.getAdapterPosition()));
            }
        });
    }
}
