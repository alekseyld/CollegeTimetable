package com.alekseyld.collegetimetable.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class SettingsResponse {

    @SerializedName("rootUrl")
    private final String rootUrl;

    @SerializedName("abbreviationMap")
    private final Map<String, String> abbreviationMap;

    @SerializedName("neftGroup")
    private final List<String> neftGroup;

    public SettingsResponse(String rootUrl, Map<String, String> abbreviationMap, List<String> neftGroup) {
        this.rootUrl = rootUrl;
        this.abbreviationMap = abbreviationMap;
        this.neftGroup = neftGroup;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public Map<String, String> getAbbreviationMap() {
        return abbreviationMap;
    }

    public List<String> getNeftGroup() {
        return neftGroup;
    }
}
