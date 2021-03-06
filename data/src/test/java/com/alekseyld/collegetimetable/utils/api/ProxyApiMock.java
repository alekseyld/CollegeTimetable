package com.alekseyld.collegetimetable.utils.api;

import com.alekseyld.collegetimetable.api.ProxyApi;
import com.alekseyld.collegetimetable.entity.ApiResponse;

import rx.Observable;

public class ProxyApiMock implements ProxyApi {

    boolean fromTestPages = false;
    String testPages = "https://alekseyld.github.io/CollegeTimetable/timetable_app_all_week";

    @Override
    public Observable<ApiResponse> getUrl(String urls) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(0);

        if (fromTestPages) {
            apiResponse.setResult(testPages);
        } else {
            apiResponse.setResult(urls.replace("uecoll.ru", "ovswg33mnqxhe5i.nblz.ru"));
        }

        return Observable.just(apiResponse);
    }

    public boolean isFromTestPages() {
        return fromTestPages;
    }

    public ProxyApiMock setFromTestPages(boolean fromTestPages) {
        this.fromTestPages = fromTestPages;
        return this;
    }

    public String getTestPages() {
        return testPages;
    }

    public ProxyApiMock setTestPages(String testPages) {
        this.testPages = testPages;
        return this;
    }
}
