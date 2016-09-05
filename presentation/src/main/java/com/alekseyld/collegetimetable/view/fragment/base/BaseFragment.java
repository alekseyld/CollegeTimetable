package com.alekseyld.collegetimetable.view.fragment.base;

/**
 * Created by Alekseyld on 02.09.2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.alekseyld.collegetimetable.internal.di.HasComponent;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.presenter.base.Presenter;
import com.alekseyld.collegetimetable.view.BaseView;
import com.alekseyld.collegetimetable.view.activity.base.BaseActivity;

import javax.inject.Inject;

/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment<TPresenter extends BasePresenter> extends Fragment {

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
        mPresenter.destroy();
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

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    protected void initialize(){
        initializeInjections();
        mPresenter.setView(this);
    }

    public BaseActivity getAct(){
        return (BaseActivity)this.getActivity();
    }

    protected abstract void initializeInjections();
}
