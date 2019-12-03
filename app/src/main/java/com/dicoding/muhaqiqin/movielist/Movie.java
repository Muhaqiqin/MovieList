package com.dicoding.muhaqiqin.movielist;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String idMovie;
    private String judul;
    private String studio;
    private String photo;
    private String rating;
    private String release;

    public String getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(String idMovie) {
        this.idMovie = idMovie;
    }

    private String Desc;

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public Movie() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idMovie);
        dest.writeString(this.judul);
        dest.writeString(this.studio);
        dest.writeString(this.photo);
        dest.writeString(this.rating);
        dest.writeString(this.release);
        dest.writeString(this.Desc);
    }

    protected Movie(Parcel in) {
        this.idMovie = in.readString();
        this.judul = in.readString();
        this.studio = in.readString();
        this.photo = in.readString();
        this.rating = in.readString();
        this.release = in.readString();
        this.Desc = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
