package com.alekseyld.collegetimetable.dto;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class SettingsResponse {

    @SerializedName("rootUrl")
    @Nullable
    private final String rootUrl;

    @SerializedName("abbreviationMap")
    @Nullable
    private final Map<String, String> abbreviationMap;

    @SerializedName("neftGroup")
    @Nullable
    private final List<String> neftGroup;

    public SettingsResponse(
        @Nullable String rootUrl,
        @Nullable Map<String, String> abbreviationMap,
        @Nullable List<String> neftGroup
    ) {
        this.rootUrl = rootUrl;
        this.abbreviationMap = abbreviationMap;
        this.neftGroup = neftGroup;
    }

    @Nullable
    public String getRootUrl() {
        return rootUrl;
    }

    @Nullable
    public Map<String, String> getAbbreviationMap() {
        return abbreviationMap;
    }

    @Nullable
    public List<String> getNeftGroup() {
        return neftGroup;
    }
}
