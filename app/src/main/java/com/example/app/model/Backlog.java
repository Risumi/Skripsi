package com.example.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Backlog implements Parcelable {
    private String id;
    private String idProject;
    private String idSprint;
    private String epicName;
    private String name;
    private String status;
    private String assignee;
    private String description;
    private Date createddate;
    private String createdby;
    private Date modifieddate;
    private String modifiedby;

    public Backlog(String id, String idProject, String idSprint, String epicName, String name, String status, String assignee, String description, Date createddate, String createdby, Date modifieddate, String modifiedby) {
        this.id = id;
        this.idProject = idProject;
        this.idSprint = idSprint;
        this.epicName = epicName;
        this.name = name;
        this.status = status;
        this.assignee = assignee;
        this.description = description;
        this.createddate = createddate;
        this.createdby = createdby;
        this.modifieddate = modifieddate;
        this.modifiedby = modifiedby;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdProject() {
        return idProject;
    }

    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }

    public String getIdSprint() {
        return idSprint;
    }

    public void setIdSprint(String idSprint) {
        this.idSprint = idSprint;
    }

    public String getEpicName() {
        return epicName;
    }

    public void setEpicName(String epicName) {
        this.epicName = epicName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(Date modifieddate) {
        this.modifieddate = modifieddate;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    protected Backlog(Parcel in) {
        id = in.readString();
        idProject = in.readString();
        idSprint = in.readString();
        epicName = in.readString();
        name = in.readString();
        status = in.readString();
        assignee = in.readString();
        description = in.readString();
        long tmpCreateddate = in.readLong();
        createddate = tmpCreateddate != -1 ? new Date(tmpCreateddate) : null;
        createdby = in.readString();
        long tmpModifieddate = in.readLong();
        modifieddate = tmpModifieddate != -1 ? new Date(tmpModifieddate) : null;
        modifiedby = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(idProject);
        dest.writeString(idSprint);
        dest.writeString(epicName);
        dest.writeString(name);
        dest.writeString(status);
        dest.writeString(assignee);
        dest.writeString(description);
        dest.writeLong(createddate != null ? createddate.getTime() : -1L);
        dest.writeString(createdby);
        dest.writeLong(modifieddate != null ? modifieddate.getTime() : -1L);
        dest.writeString(modifiedby);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Backlog> CREATOR = new Parcelable.Creator<Backlog>() {
        @Override
        public Backlog createFromParcel(Parcel in) {
            return new Backlog(in);
        }

        @Override
        public Backlog[] newArray(int size) {
            return new Backlog[size];
        }
    };
}