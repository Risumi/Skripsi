package com.example.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Sprint implements Parcelable {
    String id;
    String idProject;
    Date begda;
    Date endda;
    String sprintGoal;

    public Sprint(String id, String idProject, Date begda, Date endda, String sprintGoal) {
        this.id = id;
        this.idProject = idProject;
        this.begda = begda;
        this.endda = endda;
        this.sprintGoal = sprintGoal;
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

    public String getSprintGoal() {
        return sprintGoal;
    }

    public void setSprintGoal(String sprintGoal) {
        this.sprintGoal = sprintGoal;
    }


    protected Sprint(Parcel in) {
        id = in.readString();
        idProject = in.readString();
        long tmpBegdda = in.readLong();
        begda = tmpBegdda != -1 ? new Date(tmpBegdda) : null;
        long tmpEndda = in.readLong();
        endda = tmpEndda != -1 ? new Date(tmpEndda) : null;
        sprintGoal = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(idProject);
        dest.writeLong(begda != null ? begda.getTime() : -1L);
        dest.writeLong(endda != null ? endda.getTime() : -1L);
        dest.writeString(sprintGoal);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Sprint> CREATOR = new Parcelable.Creator<Sprint>() {
        @Override
        public Sprint createFromParcel(Parcel in) {
            return new Sprint(in);
        }

        @Override
        public Sprint[] newArray(int size) {
            return new Sprint[size];
        }
    };
}
