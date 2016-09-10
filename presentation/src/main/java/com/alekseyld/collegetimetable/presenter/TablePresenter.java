package com.alekseyld.collegetimetable.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alekseyld.collegetimetable.TableWrapper;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.subscriber.DefaultSubscriber;
import com.alekseyld.collegetimetable.usecase.GetTableUseCase;
import com.alekseyld.collegetimetable.view.TableView;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TablePresenter extends BasePresenter<TableView>{

    private GetTableUseCase mGetTableUseCase;

    @Inject
    TablePresenter(GetTableUseCase getTableUseCase){
        mGetTableUseCase = getTableUseCase;
    }

    public void getTimeTable(){
        mView.showLoading();

        mGetTableUseCase.setOnline(isOnline());
        mGetTableUseCase.setGroup(mView.getGroup());
        mGetTableUseCase.execute(new DefaultSubscriber<TableWrapper>(){
            @Override
            public void onNext(TableWrapper tableWrapper){
                mView.setTimeTable(tableWrapper);
            }

            @Override
            public void onError(Throwable e) {
                if(e instanceof HttpException && e.getMessage().contains("404")){
                    mView.showError("404, прокси сервер не доступен");
                }else {
                    mView.showError(e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onCompleted() {
                mView.hideLoading();
            }
        });

    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mView.context().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
