package com.alekseyld.collegetimetable.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alekseyld.collegetimetable.SettingsWrapper;
import com.alekseyld.collegetimetable.TableWrapper;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.subscriber.BaseSubscriber;
import com.alekseyld.collegetimetable.usecase.GetSettingsUseCase;
import com.alekseyld.collegetimetable.usecase.GetTableFromOfflineUseCase;
import com.alekseyld.collegetimetable.usecase.GetTableFromOnlineUseCase;
import com.alekseyld.collegetimetable.view.TableView;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TablePresenter extends BasePresenter<TableView>{

    private GetTableFromOnlineUseCase mGetTableFromOnlineUseCase;
    private GetTableFromOfflineUseCase mGetTableFromOfflineUseCase;

    private GetSettingsUseCase mGetSettingsUseCase;

    private SettingsWrapper mSettings;

    @Inject
    TablePresenter(GetTableFromOnlineUseCase getTableFromOnlineUseCase,
                   GetSettingsUseCase getSettingsUseCase,
                   GetTableFromOfflineUseCase getTableFromOfflineUseCase){

        mGetTableFromOnlineUseCase = getTableFromOnlineUseCase;
        mGetSettingsUseCase = getSettingsUseCase;
        mGetTableFromOfflineUseCase = getTableFromOfflineUseCase;
    }

    @Override
    public void resume() {
//        mView.showLoading();
        mGetSettingsUseCase.execute(new BaseSubscriber<SettingsWrapper>(){
            @Override
            public void onNext(SettingsWrapper settingsWrapper) {
                mSettings = settingsWrapper;
            }

            @Override
            public void onCompleted() {
                mView.presenterReady();
                mView.hideLoading();
            }
        });
    }

    public String getGroup(){
        return mSettings.getNotificationGroup();
    }

    public void getTimeTable(){
        mView.showLoading();

        mGetTableFromOnlineUseCase.setOnline(isOnline());
        mGetTableFromOnlineUseCase.setGroup(mView.getGroup());
        mGetTableFromOnlineUseCase.execute(new BaseSubscriber<TableWrapper>(){
            @Override
            public void onNext(TableWrapper tableWrapper){
//                Log.d("test", "TableWrapper offline onNext" + tableWrapper.getTimeTable().size());
                mView.setTimeTable(tableWrapper);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mView.showError(e.getMessage());
                mView.hideLoading();
            }

            @Override
            public void onCompleted() {
                mView.hideLoading();
            }
        });

    }

    public void getTableFromOffline(){
        mView.showLoading();

        mGetTableFromOfflineUseCase.setGroup(mView.getGroup());
        mGetTableFromOfflineUseCase.execute(new BaseSubscriber<TableWrapper>(){
            @Override
            public void onNext(TableWrapper tableWrapper){
                mView.setTimeTable(tableWrapper);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mView.showError("Он мертв, Джим");
                mView.hideLoading();
            }

            @Override
            public void onCompleted() {
                mView.hideLoading();
                if(mView.getTimeTable() == null
                        || mView.getTimeTable().getTimeTable().size() == 0){
                    mView.showMessage();
                }
            }
        });
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
