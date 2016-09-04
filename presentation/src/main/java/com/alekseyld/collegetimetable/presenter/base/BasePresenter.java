package com.alekseyld.collegetimetable.presenter.base;

import com.alekseyld.collegetimetable.view.BaseView;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class BasePresenter<TView> implements Presenter {

    protected TView mView;

    public void setView(TView mView) {
        this.mView = mView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
