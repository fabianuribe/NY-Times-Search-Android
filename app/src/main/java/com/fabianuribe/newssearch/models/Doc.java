package com.fabianuribe.newssearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by uribe on 1/31/17.
 */

public class Doc implements Parcelable {
    @SerializedName("_id") String id;
    @SerializedName("web_url") String webUrl;
    String snippet;
    String source;
    ArrayList<Multimedia> multimedia;


    public String getId() {
        return id;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public String getSource() {
        return source;
    }

    public ArrayList<Multimedia> getMultimedia() {
        return multimedia;
    }

    public String getThumbnail() {
        String thumbnailUrl = null;
        if (multimedia.size() > 0 && multimedia.get(0).getUrl() != null) {
            thumbnailUrl = String.format("http://www.nytimes.com/%s", multimedia.get(0).getUrl());
        }
        return thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.webUrl);
        dest.writeString(this.snippet);
        dest.writeString(this.source);
        dest.writeTypedList(this.multimedia);
    }

    public Doc() {
    }

    protected Doc(Parcel in) {
        this.id = in.readString();
        this.webUrl = in.readString();
        this.snippet = in.readString();
        this.source = in.readString();
        this.multimedia = in.createTypedArrayList(Multimedia.CREATOR);
    }

    public static final Creator<Doc> CREATOR = new Creator<Doc>() {
        @Override
        public Doc createFromParcel(Parcel source) {
            return new Doc(source);
        }

        @Override
        public Doc[] newArray(int size) {
            return new Doc[size];
        }
    };
}
