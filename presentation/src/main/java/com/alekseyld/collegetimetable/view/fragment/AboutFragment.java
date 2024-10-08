package com.alekseyld.collegetimetable.view.fragment;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.alekseyld.collegetimetable.BuildConfig;
import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.databinding.FragmentAboutBinding;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.presenter.AboutPresenter;
import com.alekseyld.collegetimetable.view.AboutView;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;

/**
 * Created by Alekseyld on 08.09.2016.
 */

public class AboutFragment extends BaseFragment<AboutPresenter> implements AboutView {

    private FragmentAboutBinding binding;

    private int debug = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(inflater, container, false);

        if (getActivity() != null)
            getActivity().setTitle(R.string.about);

        String[] about = new String[]{
            getString(R.string.info_r),
            "Версия: " + BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ") " + " " + BuildConfig.BUILD_TYPE,
            getString(R.string.info_star),
            getString(R.string.disclaimer),
            getString(R.string.github),
            getString(R.string.group_about)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, about);

        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (i) {
                case 0:
                    openLink("https://vk.com/alekseyld");
                    break;
                case 1:
                    if (++debug % 3 == 0) debugInfo();
                    break;
                case 2:
                    Uri uri = Uri.parse("market://details?id=" + requireContext().getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    try {
                        startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + requireContext().getPackageName())));
                    }
                    break;
                case 3:
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setTitle(getString(R.string.disclaimer))
                        .setMessage("Все может баговать, не работатать и тд. Если нашли ошибку, сообщите мне о ней.")
                        .setCancelable(true);
                    AlertDialog alert = builder.create();
                    alert.show();
                    break;
                case 4:
                    openLink("https://github.com/alekseyld/CollegeTimetable");
                    break;
                case 5:
                    openLink("https://vk.com/utec_time");
                    break;
            }

        });

        binding.debugInfo.setOnClickListener(v -> onClickDebugInfo());

        return binding.getRoot();
    }

    public void debugInfo() {
        binding.debugInfo.setMovementMethod(new ScrollingMovementMethod());
        binding.debugInfo.setText(mPresenter.getDebugText());
    }

    private void openLink(String url) {
        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        intent1.setData(Uri.parse(url));
        startActivity(intent1);
    }

    public void onClickDebugInfo() {
        ClipboardManager clipboard = (ClipboardManager) getBaseActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(binding.debugInfo.getText(), binding.debugInfo.getText());
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            showToastMessage("Отладочная информация скопирована");
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }

    @Override
    protected void initializeInjections() {
        getComponent(MainComponent.class).inject(this);
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }
}
