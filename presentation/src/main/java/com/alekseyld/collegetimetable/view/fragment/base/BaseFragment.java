package com.alekseyld.collegetimetable.view.fragment.base;

/**
 * Created by Alekseyld on 02.09.2016.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.widget.Toast;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.HasComponent;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.view.BaseView;
import com.alekseyld.collegetimetable.view.activity.base.BaseActivity;

import javax.inject.Inject;


/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment<TPresenter extends BasePresenter> extends Fragment implements BaseView {

    @Inject
    protected TPresenter mPresenter;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
        } else {
            Log.e("BaseFragment", "onDestroy");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void showAlertDialog(String title,
                                @Nullable String text,
                                String positiveText,
                                String negativeText,
                                DialogInterface.OnClickListener positiveOperation,
                                @Nullable DialogInterface.OnClickListener negativeOperation) {
        if (getContext() == null) return;

        AlertDialog.Builder builder;

        if (BaseActivity.isDarkMode) {
            builder = new AlertDialog.Builder(getContext(), R.style.DarkDialogTheme);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }

        builder.setTitle(title)
                .setMessage(text)
                .setCancelable(false)
                .setPositiveButton(positiveText, positiveOperation)
                .setNegativeButton(negativeText, negativeOperation != null ? negativeOperation : new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    protected void initialize() {
        initializeInjections();
        mPresenter.setView(this);
    }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) this.getActivity();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    protected abstract void initializeInjections();
}
