package com.alekseyld.collegetimetable.service;

import java.util.List;
import java.util.Map;

public interface GroupService {

    String getGroupUrl(String group);
    String getGroupUrl(String root, String group);
    String getGroupUrl(String root, String group, Map<String, String> abbreviationMap, List<String> neftGroup);

}
