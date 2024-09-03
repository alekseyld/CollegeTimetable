package com.alekseyld.collegetimetable.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.presenter.SettingsFavoritePresenter;
import com.alekseyld.collegetimetable.view.adapter.base.BasePresenterAdapter;
import com.alekseyld.collegetimetable.view.adapter.holder.FavoriteGroupViewHolder;

import org.jetbrains.annotations.NotNull;


/**
 * Created by Alekseyld on 10.09.2017.
 */

public class FavoriteGroupAdapter extends BasePresenterAdapter<String, SettingsFavoritePresenter, FavoriteGroupViewHolder> {

    private final Context mContext;
    private final boolean teacherMode;

    public FavoriteGroupAdapter(SettingsFavoritePresenter presenter, Context context, boolean teacherMode) {
        super(presenter);
        mContext = context;
        this.teacherMode = teacherMode;
    }

    @Override
    @NotNull
    public FavoriteGroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.list_favorite_group, parent, false);

        return new FavoriteGroupViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FavoriteGroupViewHolder holder, int position) {
        holder.getGroupName().setText(getItems().get(holder.getAdapterPosition()));

        holder.getDelete().setOnClickListener(v -> new AlertDialog.Builder(mContext)
            .setTitle("Вы точно хотите удалить группу из избранных")
            .setPositiveButton("Да", (dialog, which) -> {
                mPresenter.removeFavoriteGroup(getItems().get(holder.getAdapterPosition()), teacherMode);
            })
            .setNegativeButton("Нет", (dialog, which) -> dialog.dismiss())
            .show());
    }
}
