package com.alekseyld.collegetimetable.repository.base;

import androidx.annotation.Nullable;

import com.alekseyld.collegetimetable.dto.TimeTableDto;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public interface TableRepository {

    String NAME_FILE = "DataStorage";
    String TIMETABLE_KEY = "TimeTable";
    String TIMETABLE_NEW_KEY = "TIMETABLE_NEW_KEY";
    String DAYS_KEY = "Days";
    String DOC_KEY = "Doc";

    @Nullable
    TimeTableDto getTimeTable(String group);

    boolean putTimeTable(TimeTableDto timeTable, String group);
}
