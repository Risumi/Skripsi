package com.example.app.model;

public class Progress {
    private String idProject;
    private float count;
    private float completed;

    public Progress(String idProject, float count, float completed) {
        this.idProject = idProject;
        this.count = count;
        this.completed = completed;
    }

    public String getIdProject() {
        return idProject;
    }

    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public float getCompleted() {
        return completed;
    }

    public void setCompleted(float completed) {
        this.completed = completed;
    }
}
