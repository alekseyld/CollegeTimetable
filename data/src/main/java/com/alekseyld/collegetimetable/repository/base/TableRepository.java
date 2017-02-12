package com.alekseyld.collegetimetable.repository.base;

import com.alekseyld.collegetimetable.TableWrapper;

import org.jsoup.nodes.Document;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public interface TableRepository {

    String NAME_FILE = "DataStorage";
    String TIMETABLE_KEY = "TimeTable";
    String DAYS_KEY = "Days";
    String DOC_KEY = "Doc";

    TableWrapper getTimeTable(String group);
    String getDocument();

    boolean putTimeTable(TableWrapper tableWrapper, String group);
    void putDocument(Document document);

    void put(TableWrapper tableWrapper, Document document, String group);

}
