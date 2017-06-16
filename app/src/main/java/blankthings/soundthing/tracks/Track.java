package blankthings.soundthing.tracks;

/**
 * Created by iosif on 6/16/17.
 */

public class Track {

    private String path;
    private String title;
    private String artist;

    public Track(String path, String title, String artist) {
        this.path = path;
        this.title = title;
        this.artist = artist;
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
}
