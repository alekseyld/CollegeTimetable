package com.alekseyld.collegetimetable.api;

import com.alekseyld.collegetimetable.entity.ApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public interface ProxyApi {

    @GET("anonymize?")
    Observable<ApiResponse> getUrl(@Query("url") String urls);

}
