package com.alekseyld.collegetimetable.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.TableWrapper;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.presenter.TablePresenter;
import com.alekseyld.collegetimetable.view.TableView;
import com.alekseyld.collegetimetable.view.adapter.TableAdapter;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TableFragment extends BaseFragment<TablePresenter> implements TableView{

    public static TableFragment newInstance(){
        return new TableFragment();
    }

    @BindView(R.id.recView)
    RecyclerView mTableList;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private RecyclerView.LayoutManager mLayoutManager;
    private TableAdapter mTableAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_table, container, false);
        ButterKnife.bind(this, v);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        mTableList.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mTableList.setLayoutManager(mLayoutManager);

        mTableAdapter = new TableAdapter();
        mTableList.setAdapter(mTableAdapter);

        return v;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.getTimeTable();
    }

    @Override
    public void setTimeTable(TableWrapper timeTable) {
        mTableAdapter.setTableWrapper(timeTable);
    }

    private void refreshItems() {
        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        mTableList.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mTableList.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }

    @Override
    public Context context() {
        return getActivity();
    }

    @Override
    protected void initializeInjections() {
        getComponent(MainComponent.class).inject(this);
    }

}
