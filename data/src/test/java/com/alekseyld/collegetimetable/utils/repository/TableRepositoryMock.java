package com.alekseyld.collegetimetable.utils.repository;

import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.repository.base.TableRepository;

import org.jsoup.nodes.Document;

public class TableRepositoryMock implements TableRepository {

    @Override
    public TimeTable getTimeTable(String group) {
        return null;
    }

    @Override
    public String getDocument() {
        return null;
    }

    @Override
    public boolean putTimeTable(TimeTable timeTable, String group) {
        return true;
    }

    @Override
    public void putDocument(Document document) {

    }

    @Override
    public void put(TimeTable timeTable, Document document, String group) {

    }
}
