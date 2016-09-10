package com.alekseyld.collegetimetable.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.TableWrapper;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.presenter.TablePresenter;
import com.alekseyld.collegetimetable.view.TableView;
import com.alekseyld.collegetimetable.view.activity.base.BaseActivity;
import com.alekseyld.collegetimetable.view.adapter.TableAdapter;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;
import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.GROUP_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TableFragment extends BaseFragment<TablePresenter> implements TableView{

    public static TableFragment newInstance(String group){
        TableFragment tableFragment = new TableFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GROUP_KEY, group);
        tableFragment.setArguments(bundle);
        return tableFragment;
    }

    @BindView(R.id.recView)
    RecyclerView mTableList;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private RecyclerView.LayoutManager mLayoutManager;
    private TableAdapter mTableAdapter;

    @BindString(R.string.app_name)
    String app_name;

    private String mGroup = "2 АПП-1";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_table, container, false);
        ButterKnife.bind(this, v);

        if(!getArguments().isEmpty()) {
            mGroup = getArguments().getString(GROUP_KEY);
            if(mGroup.equals("")){
                mGroup = context().getSharedPreferences(NAME_FILE, MODE_PRIVATE).getString(GROUP_KEY, "2 АПП-1");
            }
            getActivity().setTitle("Группа: "+ mGroup);
        }else{
            getActivity().setTitle(R.string.app_name);
        }

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
        mPresenter.getTimeTable();
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
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public Context context() {
        return getActivity();
    }

    @Override
    protected void initializeInjections() {
        getComponent(MainComponent.class).inject(this);
    }

    @Override
    public String getGroup() {
        return mGroup;
    }
}
