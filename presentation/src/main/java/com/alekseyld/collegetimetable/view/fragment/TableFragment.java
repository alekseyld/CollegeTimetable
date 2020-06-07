package com.alekseyld.collegetimetable.view.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.presenter.TablePresenter;
import com.alekseyld.collegetimetable.utils.DataUtils;
import com.alekseyld.collegetimetable.view.TableView;
import com.alekseyld.collegetimetable.view.adapter.TableAdapter;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.GROUP_KEY;

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

    @BindView(R.id.message)
    TextView message;

    private RecyclerView.LayoutManager mLayoutManager;
    private TableAdapter mTableAdapter;

    private Menu mMenu;

    @BindString(R.string.app_name)
    String app_name;

    private String mGroup = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_table, container, false);

        ButterKnife.bind(this, v);

        if (getActivity() != null)
            getActivity().setTitle(R.string.app_name);
        setHasOptionsMenu(true);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        mTableList.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mTableList.setLayoutManager(mLayoutManager);

        mTableAdapter = new TableAdapter(getContext(), mLayoutManager, this);
        mTableList.setAdapter(mTableAdapter);

        return v;
    }

    @Override
    public boolean getChangeMode() {
        return mPresenter != null && mPresenter.getChangeMode();
    }

    @Override
    public void shareDay(Bitmap image) {
        if (mPresenter != null) {
            mPresenter.shareDay(image, getContext().getCacheDir());

            Bundle b = new Bundle();
            b.putString("group", getGroup());
            FirebaseAnalytics.getInstance(getContext())
                    .logEvent(FirebaseAnalytics.Event.SHARE, b);

        } else {
            showError("Ошибка при отправке расписания");
        }
    }

    private void processArgumentsGroup() {
        if (getArguments() == null || !getArguments().containsKey(GROUP_KEY)) return;

        if(getArguments().containsKey(GROUP_KEY)) {
            String s = getArguments().getString(GROUP_KEY);
            mGroup = s == null || s.equals("") ? mPresenter.getGroup() : s;
        }

        if(getActivity() != null && mGroup != null && !mGroup.equals("")){
            if (DataUtils.fioPattern.matcher(mGroup).find()) {
                getActivity().setTitle("Преподаватель: " + mGroup);
            } else {
                getActivity().setTitle("Группа: " + mGroup);
            }

            Crashlytics.setString("Group", mGroup);
        }
    }

    @Override
    public void presenterReady() {
        showPhoneStatePermission();

        processArgumentsGroup();

        mPresenter.getTableFromOffline();
    }


    private void showPhoneStatePermission() {
//        if (getActivity() == null)
//            return;

//        int permission = ContextCompat.checkSelfPermission(getActivity(),
//                Manifest.permission.WRITE_EXTERNAL_STORAGE);

//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            makeRequest();
//            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setMessage("Чтобы делиться расписанием необходим доступ к SD карте.")
//                        .setTitle("Необходимо разрашение");
//
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int id) {
//                        Log.i("12", "Clicked");
//                        makeRequest();
//                    }
//                });
//
//                AlertDialog dialog = builder.create();
//                dialog.show();

//            } else {
//                makeRequest();
//            }
//        }

    }

    protected void makeRequest() {
        if (getActivity() == null) return;

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                255);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mMenu = menu;
        mMenu.clear();
        inflater.inflate(R.menu.menu_table, menu);

        Drawable drawable = menu.findItem(R.id.action_info).getIcon();
        if(drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        }

        onTimeTableUpdate();

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_info:
                if (getTimeTable() == null || getTimeTable().getLastRefresh() == null) break;

                showToastMessage("Последнее обновление: " + getTimeTable().getLastRefresh());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onTimeTableUpdate(){
        if (getTimeTable() != null
                && getTimeTable().getLastRefresh() != null
                && mMenu != null
                && mMenu.findItem(R.id.action_info) != null){
            mMenu.findItem(R.id.action_info).setVisible(true);
        }

    }

    @Override
    public void setTimeTable(TimeTable timeTable) {
        mTableAdapter.setTimeTable(timeTable);

        onTimeTableUpdate();
    }

    @Override
    public TimeTable getTimeTable() {
        return mTableAdapter.getTimeTable();
    }

    @Override
    public void showMessage() {
        message.setVisibility(View.VISIBLE);
    }

    private void refreshItems() {
        message.setVisibility(View.GONE);
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
        if(getActivity() != null)
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        else
            Log.e("TableFragment", "Activity is null \n"+message);
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
