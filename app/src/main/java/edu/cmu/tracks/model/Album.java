package edu.cmu.tracks.model;

public class Album {
    private float id;
    private String title;
    private String cover_medium;
    private String tracklist;

    public Album(float id, String title, String cover_medium, String tracklist) {
        this.id = id;
        this.title = title;
        this.cover_medium = cover_medium;
        this.tracklist = tracklist;
    }

    // Getter Methods

    public float getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCoverMedium() {
        return cover_medium;
    }

    public String getTracklist() {
        return tracklist;
    }

}
