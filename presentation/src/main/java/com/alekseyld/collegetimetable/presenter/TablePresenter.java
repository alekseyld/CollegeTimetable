package com.alekseyld.collegetimetable.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.rx.subscriber.BaseSubscriber;
import com.alekseyld.collegetimetable.usecase.GetSettingsUseCase;
import com.alekseyld.collegetimetable.usecase.GetTableFromOfflineUseCase;
import com.alekseyld.collegetimetable.usecase.GetTableFromServerUseCase;
import com.alekseyld.collegetimetable.utils.Utils;
import com.alekseyld.collegetimetable.view.TableView;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 02.09.2017.
 */

public class TablePresenter extends BasePresenter<TableView> {

    private GetTableFromOfflineUseCase mGetTableFromOfflineUseCase;

    private GetTableFromServerUseCase mGetTableFromServerUseCase;

    private GetSettingsUseCase mGetSettingsUseCase;

    private Settings mSettings;

    @Inject
    TablePresenter(GetSettingsUseCase getSettingsUseCase,
                   GetTableFromOfflineUseCase getTableFromOfflineUseCase,
                   GetTableFromServerUseCase getTableFromServerUseCase) {

        mGetSettingsUseCase = getSettingsUseCase;
        mGetTableFromOfflineUseCase = getTableFromOfflineUseCase;
        mGetTableFromServerUseCase = getTableFromServerUseCase;
    }

    @Override
    public void resume() {
        mGetSettingsUseCase.execute(new BaseSubscriber<Settings>() {
            @Override
            public void onNext(Settings settings) {
                mSettings = settings;
            }

            @Override
            public void onCompleted() {
                mView.presenterReady();
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

        mGetTableFromServerUseCase.setGroup(mView.getGroup());
        mGetTableFromServerUseCase.execute(new BaseSubscriber<TimeTable>() {
            @Override
            public void onNext(TimeTable timeTable) {
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
