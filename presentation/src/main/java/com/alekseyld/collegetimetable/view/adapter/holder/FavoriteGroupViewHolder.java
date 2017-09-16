package com.alekseyld.collegetimetable.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alekseyld.collegetimetable.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 16.09.2017.
 */

public class FavoriteGroupViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.group_name)
    public TextView groupName;

    @BindView(R.id.delete)
    public ImageView delete;

    public FavoriteGroupViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }
}
