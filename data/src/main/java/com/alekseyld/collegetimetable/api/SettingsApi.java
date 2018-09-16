package com.alekseyld.collegetimetable.api;

import com.alekseyld.collegetimetable.entity.SettingsResponse;

import retrofit2.http.GET;
import rx.Observable;

public interface SettingsApi {

    @GET("pref.html")
    Observable<SettingsResponse> getSettings();

}
