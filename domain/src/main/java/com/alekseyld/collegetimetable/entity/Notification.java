package com.alekseyld.collegetimetable.entity;

import java.util.Date;

/**
 * Created by Alekseyld on 03.01.2018.
 */

public class Notification {

    private String title;

    private String text;

    private String author;

    private Date date;

    public String getTitle() {
        return title;
    }

    public Notification setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getText() {
        return text;
    }

    public Notification setText(String text) {
        this.text = text;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Notification setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Notification setDate(Date date) {
        this.date = date;
        return this;
    }
}
