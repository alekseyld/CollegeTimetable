package com.alekseyld.collegetimetable.repository.base;

import com.alekseyld.collegetimetable.TableWrapper;

import org.jsoup.nodes.Document;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public interface TableRepository {

    TableWrapper getTimeTable();
    String getDocument();

    void putTimeTable(TableWrapper tableWrapper);
    void putDocument(Document document);

    void put(TableWrapper tableWrapper, Document document);

}
