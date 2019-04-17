package com.example.app;

import android.os.Parcel;
import android.os.Parcelable;

public class Project implements Parcelable {
    String name;
    String id;
    String status;
    String description;

    public Project(String name, String id, String status, String description) {
        this.name = name;
        this.id = id;
        this.status = status;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    protected Project(Parcel in) {
            name = in.readString();
            id = in.readString();
            status = in.readString();
            description = in.readString();
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest,int flags){
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(status);
        dest.writeString(description);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Project> CREATOR = new Parcelable.Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };
}
