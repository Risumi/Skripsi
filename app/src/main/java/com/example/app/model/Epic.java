package com.example.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.app.model.Backlog;

import java.util.Date;
import java.util.List;

public class Epic implements Parcelable {
    private String name;
    private String status;
    private String description;
    private String id;
    private String idProject;

    public Epic(String name, String status, String description, String id, String idProject) {
        this.name = name;
        this.status = status;
        this.description = description;
        this.id = id;
        this.idProject = idProject;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    protected Epic(Parcel in) {
        name = in.readString();
        status = in.readString();
        description = in.readString();
        id = in.readString();
        idProject = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(status);
        dest.writeString(description);
        dest.writeString(id);
        dest.writeString(idProject);
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