package com.alekseyld.collegetimetable.view.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alekseyld.collegetimetable.BuildConfig;
import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.presenter.AboutPresenter;
import com.alekseyld.collegetimetable.view.AboutView;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 08.09.2016.
 */

public class AboutFragment extends BaseFragment<AboutPresenter> implements AboutView {

    public static AboutFragment newInstance(){
        return new AboutFragment();
    }

    @BindView(R.id.listView)
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle(R.string.about);

        String[] about = new String[]{
                getString(R.string.info_r),
                "Версия: " + BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ") " + " " + BuildConfig.BUILD_TYPE,
                getString(R.string.info_star),
                getString(R.string.disclaimer),
                getString(R.string.github)
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
                        Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
                        }
                        break;
                    case 3:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle(getString(R.string.disclaimer))
                                .setMessage("Все может баговать, не работатать и тд. Если нашли ошибку, сообщите мне о ней.")
                                .setCancelable(true);
                        AlertDialog alert = builder.create();
                        alert.show();
                        break;
                    case 4:
                        String url1 = "https://github.com/alekseyld/CollegeTimetable";
                        Intent intent1 = new Intent(Intent.ACTION_VIEW);
                        intent1.setData(Uri.parse(url1));
                        startActivity(intent1);
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
    protected void initializeInjections() {
        getComponent(MainComponent.class).inject(this);
    }
}
