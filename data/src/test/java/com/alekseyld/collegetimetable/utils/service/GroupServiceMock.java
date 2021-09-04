package com.alekseyld.collegetimetable.utils.service;

import com.alekseyld.collegetimetable.service.GroupService;

import java.util.List;
import java.util.Map;

public class GroupServiceMock implements GroupService {

    @Override
    public String getGroupUrl(String group) {
        return "";
    }

    @Override
    public String getGroupUrl(String root, String group) {
        return root;
    }

    @Override
    public String getGroupUrl(String root, String group, Map<String, String> abbreviationMap, List<String> neftGroup) {
        return root;
    }
}
