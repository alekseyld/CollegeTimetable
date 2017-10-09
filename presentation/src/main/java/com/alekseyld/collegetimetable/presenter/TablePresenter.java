package com.alekseyld.collegetimetable.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.rx.subscriber.BaseSubscriber;
import com.alekseyld.collegetimetable.usecase.GetSettingsUseCase;
import com.alekseyld.collegetimetable.usecase.GetTableFromOfflineUseCase;
import com.alekseyld.collegetimetable.usecase.GetTableFromOnlineUseCase;
import com.alekseyld.collegetimetable.utils.Utils;
import com.alekseyld.collegetimetable.view.TableView;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TablePresenter extends BasePresenter<TableView> {

    private GetTableFromOnlineUseCase mGetTableFromOnlineUseCase;
    private GetTableFromOfflineUseCase mGetTableFromOfflineUseCase;

    private GetSettingsUseCase mGetSettingsUseCase;

    private Settings mSettings;

    @Inject
    TablePresenter(GetTableFromOnlineUseCase getTableFromOnlineUseCase,
                   GetSettingsUseCase getSettingsUseCase,
                   GetTableFromOfflineUseCase getTableFromOfflineUseCase) {

        mGetTableFromOnlineUseCase = getTableFromOnlineUseCase;
        mGetSettingsUseCase = getSettingsUseCase;
        mGetTableFromOfflineUseCase = getTableFromOfflineUseCase;
    }

    @Override
    public void resume() {
//        mView.showLoading();
        mGetSettingsUseCase.execute(new BaseSubscriber<Settings>() {
            @Override
            public void onNext(Settings settings) {
                mSettings = settings;
            }

            @Override
            public void onCompleted() {
                mView.presenterReady();
                mView.hideLoading();
            }
        });
    }

    public boolean getChangeMode(){
        return mSettings.getChangeMode();
    }

    public String getGroup() {
        return mSettings != null ? mSettings.getNotificationGroup() : "";
    }

    public void getTimeTable() {
        mView.showLoading();

        mGetTableFromOnlineUseCase.setOnline(isOnline());
        mGetTableFromOnlineUseCase.setGroup(mView.getGroup());
        mGetTableFromOnlineUseCase.execute(new BaseSubscriber<TimeTable>() {
            @Override
            public void onNext(TimeTable timeTable) {
//                Log.d("test", "TimeTable offline onNext" + timeTable.getDayList().size());
                mView.setTimeTable(timeTable);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
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

    public void getTableFromOffline() {
        mView.showLoading();

        mGetTableFromOfflineUseCase.setGroup(mView.getGroup());
        mGetTableFromOfflineUseCase.execute(new BaseSubscriber<TimeTable>() {
            @Override
            public void onNext(TimeTable timeTable) {
                mView.setTimeTable(timeTable);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                e.printStackTrace();
                mView.showError("Он мертв, Джим");
                mView.hideLoading();
            }

            @Override
            public void onCompleted() {
                mView.hideLoading();
                if (mView.getTimeTable() == null
                        || mView.getTimeTable().getDayList() == null
                        || mView.getTimeTable().getDayList().size() == 0) {
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

    public void shareDay(Bitmap dayByBitmap) {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("image/*");
            final Uri imageUri = Uri.fromFile(Utils.getImageFile(dayByBitmap));
            i.putExtra(Intent.EXTRA_STREAM, imageUri);

            mView.getContext().startActivity(Intent.createChooser(i, "Поделиться расписанием"));
        } catch (android.content.ActivityNotFoundException | IOException | NullPointerException ex) {
            mView.showError(ex.getMessage());
            ex.printStackTrace();
        }
    }

}
