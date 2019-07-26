package com.example.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Sprint implements Parcelable {
    private String id;
    private String idProject;
    private String name;
    private Date begda;
    private Date endda;
    private String sprintGoal;
    private String status;
    private String retrospective;
    private Date createddate;
    private String createdby;
    private Date modifieddate;
    private String modifiedby;

    public Sprint() {
    }

    public Sprint(String id, String idProject, String name, Date begda, Date endda, String sprintGoal, String status, String retrospective, Date createddate, String createdby, Date modifieddate, String modifiedby) {
        this.id = id;
        this.idProject = idProject;
        this.name = name;
        this.begda = begda;
        this.endda = endda;
        this.sprintGoal = sprintGoal;
        this.status = status;
        this.retrospective = retrospective;
        this.createddate = createddate;
        this.createdby = createdby;
        this.modifieddate = modifieddate;
        this.modifiedby = modifiedby;
    }

    protected Sprint(Parcel in) {
        id = in.readString();
        idProject = in.readString();
        name = in.readString();
        long tmpBegda = in.readLong();
        begda = tmpBegda != -1 ? new Date(tmpBegda) : null;
        long tmpEndda = in.readLong();
        endda = tmpEndda != -1 ? new Date(tmpEndda) : null;
        sprintGoal = in.readString();
        status = in.readString();
        retrospective = in.readString();
        long tmpCreateddate = in.readLong();
        createddate = tmpCreateddate != -1 ? new Date(tmpCreateddate) : null;
        createdby = in.readString();
        long tmpModifieddate = in.readLong();
        modifieddate = tmpModifieddate != -1 ? new Date(tmpModifieddate) : null;
        modifiedby = in.readString();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRetrospective() {
        return retrospective;
    }

    public void setRetrospective(String retrospective) {
        this.retrospective = retrospective;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(idProject);
        dest.writeString(name);
        dest.writeLong(begda != null ? begda.getTime() : -1L);
        dest.writeLong(endda != null ? endda.getTime() : -1L);
        dest.writeString(sprintGoal);
        dest.writeString(status);
        dest.writeString(retrospective);
        dest.writeLong(createddate != null ? createddate.getTime() : -1L);
        dest.writeString(createdby);
        dest.writeLong(modifieddate != null ? modifieddate.getTime() : -1L);
        dest.writeString(modifiedby);
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