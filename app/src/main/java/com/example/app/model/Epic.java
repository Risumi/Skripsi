package com.example.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.app.model.Backlog;

import java.util.Date;
import java.util.List;

public class Epic implements Parcelable {
    private String id;
    private String idProject;
    private String name;
    private String summary;
    private Date createddate;
    private String createdby;
    private Date modifieddate;
    private String modifiedby;

    public Epic(String id, String idProject, String name, String summary, Date createddate, String createdby, Date modifieddate, String modifiedby) {
        this.id = id;
        this.idProject = idProject;
        this.name = name;
        this.summary = summary;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    protected Epic(Parcel in) {
        id = in.readString();
        idProject = in.readString();
        name = in.readString();
        summary = in.readString();
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
        dest.writeString(name);
        dest.writeString(summary);
        dest.writeLong(createddate != null ? createddate.getTime() : -1L);
        dest.writeString(createdby);
        dest.writeLong(modifieddate != null ? modifieddate.getTime() : -1L);
        dest.writeString(modifiedby);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Epic> CREATOR = new Parcelable.Creator<Epic>() {
        @Override
        public Epic createFromParcel(Parcel in) {
            return new Epic(in);
        }

        @Override
        public Epic[] newArray(int size) {
            return new Epic[size];
        }
    };
}