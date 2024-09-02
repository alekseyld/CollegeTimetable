package com.alekseyld.collegetimetable.dto;

import com.alekseyld.collegetimetable.entity.Day;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alekseyld on 15.06.2024.
 */
public class TimeTableDto {

    @SerializedName("lastRefresh")
    private final String lastRefresh;

    @SerializedName("dayList")
    private final List<DayDto> dayList;

    @SerializedName("group")
    private final String group;

    public TimeTableDto(TimeTable timeTable) {
        this.lastRefresh = timeTable.getLastRefresh();
        this.group = timeTable.getGroup();
        this.dayList = new ArrayList<>();
        for (Day day : timeTable.getDayList()) {
            this.dayList.add(new DayDto(day));
        }
    }

    public TimeTable toEntity() {
        List<Day> dayList = new ArrayList<>();
        for (DayDto dayDto : this.dayList) {
            dayList.add(dayDto.toEntity());
        }

        return new TimeTable()
            .setLastRefresh(lastRefresh)
            .setDayList(dayList)
            .setGroup(group);
    }

    public String getGroup() {
        return group;
    }
}
