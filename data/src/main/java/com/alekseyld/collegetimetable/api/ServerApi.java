package com.alekseyld.collegetimetable.api;

import com.alekseyld.collegetimetable.entity.Notification;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.entity.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Alekseyld on 02.01.2018.
 */

public interface ServerApi {

    @FormUrlEncoded
    @POST("auth")
    Observable<String> auth(@Field("login") String login, @Field("password") String password);

    @FormUrlEncoded
    @POST("getuser")
    Observable<User> getUser(@Field("authkey") String authkey);

    @FormUrlEncoded
    @POST("notifications")
    Observable<Notification[]> getNewNotifications(@Field("userid") String userid);

    @GET("timetable?")
    Observable<TimeTable> getTimeTable(@Query("group") String group);

    @FormUrlEncoded
    @POST("changes")
    Observable<Integer> changes(@Field("authkey") String authkey);

    @FormUrlEncoded
    @POST("certificate")
    Observable<Integer> certificate(@Field("authkey") String authkey,
                                    @Field("type") int type,
                                    @Field("count") int count,
                                    @Field("fio") String  fio,
                                    @Field("group") String  group,
                                    @Field("studentid") String  studentid,
                                    @Field("district") String  district);

}
