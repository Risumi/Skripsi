package com.example.app.model;

import com.example.app.model.Backlog;

import java.util.Date;
import java.util.List;

public class Epic {
    private String name;
    private String status;
    private Date begda;
    private Date endda;
    private String description;
    private String id;
    private String idProject;
    private String idSprint;
    private List<Backlog> listBacklog;

    public Epic(String name, String description, String id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Date getBegda() {
        return begda;
    }

    public Date getEndda() {
        return endda;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getIdProject() {
        return idProject;
    }

    public String getIdSprint() {
        return idSprint;
    }

    public List<Backlog> getListBacklog() {
        return listBacklog;
    }
}
