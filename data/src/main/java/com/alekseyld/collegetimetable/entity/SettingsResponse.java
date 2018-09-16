package com.alekseyld.collegetimetable.entity;

import java.util.Map;

public class SettingsResponse {

    String rootUrl;
    Map<String, String> abbreviationMap;

    public String getRootUrl() {
        return rootUrl;
    }

    public SettingsResponse setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
        return this;
    }

    public Map<String, String> getAbbreviationMap() {
        return abbreviationMap;
    }

    public SettingsResponse setAbbreviationMap(Map<String, String> abbreviationMap) {
        this.abbreviationMap = abbreviationMap;
        return this;
    }
}
