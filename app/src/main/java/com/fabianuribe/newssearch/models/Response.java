package com.fabianuribe.newssearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by uribe on 2/1/17.
 */

public class Response implements Parcelable {
    ArrayList<Doc> docs;

    public ArrayList<Doc> getDocs() {
        return docs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.docs);
    }

    public Response() {
    }

    protected Response(Parcel in) {
        this.docs = in.createTypedArrayList(Doc.CREATOR);
    }

    public static final Parcelable.Creator<Response> CREATOR = new Parcelable.Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel source) {
            return new Response(source);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };
}
