package com.alekseyld.collegetimetable.view.fragment;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.presenter.SettingsPresenter;
import com.alekseyld.collegetimetable.view.SettingsView;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 08.09.2016.
 */

public class AboutFragment extends BaseFragment<SettingsPresenter> implements SettingsView {

    public static AboutFragment newInstance(){
        return new AboutFragment();
    }

    @BindView(R.id.listView)
    ListView listView;

    private String[] about = new String[2];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle(R.string.about);

        PackageManager manager = getActivity().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        about[0] = "Разработчик: alekseyld";
        if(info != null) {
            about[1] = "Версия: "+info.versionName;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, about);

        listView.setAdapter(adapter);

        return v;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    protected void initializeInjections() {
        getComponent(MainComponent.class).inject(this);
    }
}
