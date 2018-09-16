package com.alekseyld.collegetimetable.utils.api;

import com.alekseyld.collegetimetable.api.SettingsApi;
import com.alekseyld.collegetimetable.entity.SettingsResponse;
import com.google.gson.Gson;

import rx.Observable;

public class SettingsApiMock implements SettingsApi {

    @Override
    public Observable<SettingsResponse> getSettings() {
        String json = "{\"rootUrl\": \"http://109.195.146.243/wp-content/uploads/time/\",\"abbreviationMap\": {\"1\": \"neft/10_1_10.html\",\"Т\": \"energy/10_1_8.html\",\"Э\": \"energy/10_1_7.html\",\"С\": \"energy/10_1_10.html\",\"Б\": \"energy/10_1_9.html\",\"В\": \"neft/10_1_4.html\",\"Л\": \"energy/10_1_3.html\",\"Р\": \"energy/10_1_4.html\",\"АПП\": \"neft/10_1_1.html\",\"БНГ\": \"neft/10_1_2.html\",\"ТО\": \"neft/10_1_3.html\",\"ПНГ\": \"neft/10_1_5.html\",\"ЭНН\": \"neft/10_1_6.html\",\"ЭННУ\": \"neft/10_1_6.html\",\"ТОВ\": \"neft/10_1_7.html\",\"ИС\": \"energy/10_1_1.html\",\"ГС\": \"energy/10_1_2.html\",\"ГСУ\": \"energy/10_1_2.html\",\"РУ\": \"energy/10_1_4.html\",\"ПГ\": \"energy/10_1_5.html\",\"ТС\": \"energy/10_1_6.html\",\"ТАК\": \"energy/10_1_8.html\"}}";
        return Observable.just(new Gson().fromJson(json, SettingsResponse.class));
    }
}
