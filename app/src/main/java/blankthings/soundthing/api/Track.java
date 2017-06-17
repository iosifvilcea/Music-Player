package blankthings.soundthing.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by iosif on 6/16/17.
 */

public class Track implements Parcelable {

    private long id;
    private long duration;
    private String path;
    private String title;
    private String artist;
    private String album;

    public Track(long id, long duration, String path, String title, String artist, String album) {
        this.id = id;
        this.duration = duration;
        this.path = path;
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.duration);
        dest.writeString(this.path);
        dest.writeString(this.title);
        dest.writeString(this.artist);
        dest.writeString(this.album);
    }

    protected Track(Parcel in) {
        this.id = in.readLong();
        this.duration = in.readLong();
        this.path = in.readString();
        this.title = in.readString();
        this.artist = in.readString();
        this.album = in.readString();
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel source) {
            return new Track(source);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}
