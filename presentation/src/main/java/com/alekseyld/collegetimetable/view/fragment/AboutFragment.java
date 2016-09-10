package com.alekseyld.collegetimetable.view.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    private String[] about;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle(R.string.about);

        PackageManager manager = getActivity().getPackageManager();

        about = new String[]{
                "Разработчик: alekseyld",
                "Версия: 2.1b",
                "Оценить приложение в Google play"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, about);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        String url = "https://vk.com/alekseyld";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        break;
                    case 2:
                        Uri uri = Uri.parse("market://details?id=" + context().getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + context().getPackageName())));
                        }
                        break;
                }

            }
        });

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
