package com.alekseyld.collegetimetable.service;

import static com.alekseyld.collegetimetable.utils.DataUtils.groupPatternWithoutNum;

import com.alekseyld.collegetimetable.repository.base.SettingsRepository;
import com.alekseyld.collegetimetable.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class GroupServiceImpl implements GroupService {

    private final SettingsRepository mSettingsRepository;

    @Inject
    public GroupServiceImpl(SettingsRepository settingsRepository) {
        mSettingsRepository = settingsRepository;
    }

    @Override
    public String getGroupUrl(String group) {
        String rootUrl = mSettingsRepository.getSettings().getRootUrl();

        return getGroupUrl(rootUrl, group, null, null);
    }

    @Override
    public String getGroupUrl(String root, String group) {
        return getGroupUrl(root, group, null, null);
    }

    @Override
    public String getGroupUrl(String root, String group, Map<String, String> abbreviationMap, List<String> neftGroup) {

        if (group == null || !groupPatternWithoutNum.matcher(group).matches())
            return "";

        if (root.isEmpty()) return "";

        String url;

        if (neftGroup == null || neftGroup.isEmpty()) {
            neftGroup = new ArrayList<>() {{
                add("АПП");
                add("БНГ");
                add("В");
                add("ПНГ");
                add("ТАК");
                add("ТО");
                add("ТОВ");
                add("ЭНН");
                add("ЭННУ");
            }};
        }

        final String abbr;

        if (groupPatternWithoutNum.matcher(group).matches()) {
            abbr = group.split(" ")[1].split("-")[0];
        } else {
            abbr = group.split(" ")[1];
        }

        if (group.charAt(0) == '1' && neftGroup.contains(abbr)) {
            url = DataUtils.switchAbbr(abbreviationMap, "1");
        } else if (abbr.equals("ГС") && group.charAt(0) != '4')
            //TODO: It's hot-fix
            url = "neft/10_1_11.html";
        else {
            url = DataUtils.switchAbbr(abbreviationMap, abbr);
        }

        if (url == null) return "";

        return url.isEmpty() ? url : root + url;
    }

}
