package com.example.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Backlog implements Parcelable {
    private String name;
    private String status;
    private Date begda;
    private Date endda;
    private String assignee;
    private String description;

    public Backlog(String name, String status, Date begda, Date endda, String assignee, String description) {
        this.name = name;
        this.status = status;
        this.begda = begda;
        this.endda = endda;
        this.assignee = assignee;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getBegda() {
        return begda;
    }

    public void setBegda(Date begda) {
        this.begda = begda;
    }

    public Date getEndda() {
        return endda;
    }

    public void setEndda(Date endda) {
        this.endda = endda;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    protected Backlog(Parcel in) {
        name = in.readString();
        status = in.readString();
        long tmpBegda = in.readLong();
        begda = tmpBegda != -1 ? new Date(tmpBegda) : null;
        long tmpEndda = in.readLong();
        endda = tmpEndda != -1 ? new Date(tmpEndda) : null;
        assignee = in.readString();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(status);
        dest.writeLong(begda != null ? begda.getTime() : -1L);
        dest.writeLong(endda != null ? endda.getTime() : -1L);
        dest.writeString(assignee);
        dest.writeString(description);
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