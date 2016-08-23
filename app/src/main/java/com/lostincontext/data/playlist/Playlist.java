package com.lostincontext.data.playlist;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lostincontext.commons.images.DeezerImage;
import com.lostincontext.commons.images.DeezerImageUrlGenerator.DeezerImageType;

public class Playlist implements DeezerImage, Parcelable {

    private final int id;
    private final String title;
    private final String creator;
    private final String coverMd5;
    @DeezerImageType private final int imageType;

    @JsonCreator
    public Playlist(@JsonProperty("id") int id,
                    @JsonProperty("title") @NonNull String title,
                    @JsonProperty("creator") @NonNull String creator,
                    @JsonProperty("coverMd5") @NonNull String coverMd5,
                    @JsonProperty("imageType") @DeezerImageType int imageType) {
        this.id = id;
        this.title = title;
        this.creator = creator;
        this.coverMd5 = coverMd5;
        this.imageType = imageType;
    }

    private Playlist(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.creator = in.readString();
        this.coverMd5 = in.readString();
        //noinspection WrongConstant
        this.imageType = in.readInt();
    }

    @Override public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creator='" + creator + '\'' +
                ", coverMd5='" + coverMd5 + '\'' +
                ", imageType=" + imageType +
                '}';
    }

    //region getters & setters

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }


    public String getCreator() {
        return creator;
    }


    @Override @Nullable public String getCoverMd5() {
        return coverMd5;
    }


    @Override @DeezerImageType public int getImageType() {
        return imageType;
    }

    //endregion

    //region Parcelable

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(title);
        out.writeString(creator);
        out.writeString(coverMd5);
        out.writeInt(imageType);
    }

    public static final Parcelable.Creator<Playlist> CREATOR
            = new Parcelable.Creator<Playlist>() {
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };

    //endregion


    // endregion
}