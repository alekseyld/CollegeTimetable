package com.alekseyld.collegetimetable.repository.base;

import com.alekseyld.collegetimetable.entity.TimeTable;

import org.jsoup.nodes.Document;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public interface TableRepository {

    String NAME_FILE = "DataStorage";
    String TIMETABLE_KEY = "TimeTable";
    String TIMETABLE_NEW_KEY = "TIMETABLE_NEW_KEY";
    String DAYS_KEY = "Days";
    String DOC_KEY = "Doc";

    TimeTable getTimeTable(String group);
    String getDocument();

    boolean putTimeTable(TimeTable timeTable, String group);
    void putDocument(Document document);

    void put(TimeTable timeTable, Document document, String group);

}
