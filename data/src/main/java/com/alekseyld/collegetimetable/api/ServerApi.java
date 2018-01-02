package com.alekseyld.collegetimetable.api;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Alekseyld on 02.01.2018.
 */

public interface ServerApi {

    @FormUrlEncoded
    @POST("auth")
    Observable<String> auth(@Field("login") String login, @Field("password") String password);

}
