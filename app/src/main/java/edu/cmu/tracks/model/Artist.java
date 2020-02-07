package edu.cmu.tracks.model;

public class Artist {
    private int id;
    private String name;
    private String picture_medium;
    private String tracklist;

    public Artist(int id, String name, String picture_medium, String tracklist) {
        this.id = id;
        this.name = name;
        this.picture_medium = picture_medium;
        this.tracklist = tracklist;
    }

    // Getter Methods

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture_medium() {
        return picture_medium;
    }

    public String getTracklist() {
        return tracklist;
    }

    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPicture_medium(String picture_medium) {
        this.picture_medium = picture_medium;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }
}
