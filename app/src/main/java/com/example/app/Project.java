package com.example.app;

import android.os.Parcel;
import android.os.Parcelable;

public class Project implements Parcelable {
    String name;
    String id;
    Boolean isSprinting;

    public Project(String name, String id, Boolean isSprinting) {
        this.name = name;
        this.id = id;
        this.isSprinting = isSprinting;
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

    public Boolean getSprinting() {
        return isSprinting;
    }

    public void setSprinting(Boolean sprinting) {
        isSprinting = sprinting;
    }

    protected Project(Parcel in) {
        name = in.readString();
        id = in.readString();
        byte isSprintingVal = in.readByte();
        isSprinting = isSprintingVal == 0x02 ? null : isSprintingVal != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        if (isSprinting == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isSprinting ? 0x01 : 0x00));
        }
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