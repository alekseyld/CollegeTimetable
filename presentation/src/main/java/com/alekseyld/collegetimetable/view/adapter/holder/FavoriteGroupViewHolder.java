package com.alekseyld.collegetimetable.view.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alekseyld.collegetimetable.databinding.ListFavoriteGroupBinding;

/**
 * Created by Alekseyld on 16.09.2017.
 */

public class FavoriteGroupViewHolder extends RecyclerView.ViewHolder {

    private final ListFavoriteGroupBinding binding;

    public FavoriteGroupViewHolder(View v) {
        super(v);
        binding = ListFavoriteGroupBinding.bind(v);
    }

    public TextView getGroupName() {
        return binding.groupName;
    }

    public ImageView getDelete() {
        return binding.delete;
    }
}
