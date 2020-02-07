package edu.cmu.tracks.model;

public class Track {
    private int id;
    private String title;
    private String preview;
    private Artist ArtistObject;
    private Album AlbumObject;

    public Track(int id, String title, String preview) {
        this.id = id;
        this.title = title;
        this.preview = preview;
    }
    // Getter Methods

    public int getId() { return id; }

    public String getTitle() {
        return title;
    }

    public String getPreview() {
        return preview;
    }

    public Artist getArtist() {
        return ArtistObject;
    }

    public Album getAlbum() {
        return AlbumObject;
    }

    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public void setArtist(Artist artistObject) {
        this.ArtistObject = artistObject;
    }

    public void setAlbum(Album albumObject) {
        this.AlbumObject = albumObject;
    }

}
