package com.example.app.model;

import org.joda.time.DateTime;

public class ProjectHistory {
    private String message;
    private DateTime dateTime;

    public ProjectHistory(String message, DateTime dateTime) {
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public DateTime getDateTime() {
        return dateTime;
    }
}
