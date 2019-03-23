package com.example.app;

import android.os.Parcel;
import android.os.Parcelable;

public class Project implements Parcelable {
    String name;
    String type;
//    int image;

    public Project(String name, String type) {
        this.name = name;
        this.type = type;
//        this.image = image;
    }

    protected Project(Parcel in) {
        name = in.readString();
        type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
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