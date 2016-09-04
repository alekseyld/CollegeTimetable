package com.alekseyld.collegetimetable.service;

import android.util.Log;

import com.alekseyld.collegetimetable.TableWrapper;
import com.alekseyld.collegetimetable.api.ProxyApi;
import com.alekseyld.collegetimetable.entity.ApiResponse;
import com.alekseyld.collegetimetable.repository.base.TableRepository;
import com.alekseyld.collegetimetable.utils.DataUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class TableServiceImpl implements TableService{

    private TableRepository mTimetableRepository;
    private ProxyApi urlApi;


    @Inject
    TableServiceImpl(TableRepository tableRepository, Retrofit restAdapter){
        mTimetableRepository = tableRepository;
        urlApi = restAdapter.create(ProxyApi.class);
    }

    @Override
    public Observable<TableWrapper> getTimetable() {
        return urlApi.getUrl("http://uecoll.ru/wp-content/uploads/time/neft/10_1_1.html")
                .flatMap(url -> {
                    Log.d("TimeTableUrl", url.getResult());
                    Document document = null;
                    try {
                        document = Jsoup.connect(url.getResult()).get();
                    }catch (Exception e){
                        return Observable.error(new Error(e.getMessage()));
                    }
                    //TODO Группу из настроек
                    return Observable.just(DataUtils.parseDocument(document, "2 АПП-1"));
                });
    }
}
