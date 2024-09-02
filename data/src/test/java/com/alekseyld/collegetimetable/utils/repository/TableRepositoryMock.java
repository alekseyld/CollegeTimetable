package com.alekseyld.collegetimetable.utils.repository;

import com.alekseyld.collegetimetable.dto.TimeTableDto;
import com.alekseyld.collegetimetable.repository.base.TableRepository;

public class TableRepositoryMock implements TableRepository {

    @Override
    public TimeTableDto getTimeTable(String group) {
        return null;
    }

    @Override
    public boolean putTimeTable(TimeTableDto timeTable, String group) {
        return true;
    }
}
