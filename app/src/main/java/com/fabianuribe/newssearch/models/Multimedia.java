package com.fabianuribe.newssearch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by uribe on 1/31/17.
 */

public class Multimedia implements Parcelable {
    String caption;
    String url;
    String height;
    String rank;
    String subtype;
    String type;
    String width;

    public String getCaption() {
        return caption;
    }

    public String getUrl() {
        return url;
    }

    public String getHeight() {
        return height;
    }

    public String getRank() {
        return rank;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getType() {
        return type;
    }

    public String getWidth() {
        return width;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.caption);
        dest.writeString(this.url);
        dest.writeString(this.height);
        dest.writeString(this.rank);
        dest.writeString(this.subtype);
        dest.writeString(this.type);
        dest.writeString(this.width);
    }

    public Multimedia() {
    }

    protected Multimedia(Parcel in) {
        this.caption = in.readString();
        this.url = in.readString();
        this.height = in.readString();
        this.rank = in.readString();
        this.subtype = in.readString();
        this.type = in.readString();
        this.width = in.readString();
    }

    public static final Creator<Multimedia> CREATOR = new Creator<Multimedia>() {
        @Override
        public Multimedia createFromParcel(Parcel source) {
            return new Multimedia(source);
        }

        @Override
        public Multimedia[] newArray(int size) {
            return new Multimedia[size];
        }
    };
}
