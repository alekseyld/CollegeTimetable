package com.alekseyld.collegetimetable.presenter;

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

    GetTableUseCase mGetTableUseCase;

    @Inject
    TablePresenter(GetTableUseCase getTableUseCase){
        mGetTableUseCase = getTableUseCase;
    }

    public void getTimeTable(){
        mView.showLoading();

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


//        TableWrapper tableWrapper = new TableWrapper();
//
//        HashMap<TableWrapper.Day, HashMap<TableWrapper.Lesson, String>> table = new HashMap<>();
//
//        HashMap<TableWrapper.Lesson, String> lessons = new HashMap<>();
//
//        lessons.put(TableWrapper.Lesson.lesson0, "");
//        lessons.put(TableWrapper.Lesson.lesson1, "Физика");
//        lessons.put(TableWrapper.Lesson.lesson2, "Физика");
//        lessons.put(TableWrapper.Lesson.lesson3, "Физика");
//        lessons.put(TableWrapper.Lesson.lesson4, "");
//        lessons.put(TableWrapper.Lesson.lesson5, "");
//        lessons.put(TableWrapper.Lesson.lesson6, "");
//
//        table.put(TableWrapper.Day.Mon, lessons);
//        table.put(TableWrapper.Day.Tue, lessons);
//        table.put(TableWrapper.Day.Wed, lessons);
//        table.put(TableWrapper.Day.Thu, lessons);
//        table.put(TableWrapper.Day.Friday, lessons);
//        table.put(TableWrapper.Day.Saturday, lessons);
//
//        tableWrapper.setTimeTable(table);
//
//        mView.setTimeTable(tableWrapper);
    }

}
