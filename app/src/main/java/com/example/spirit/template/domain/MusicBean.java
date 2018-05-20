package com.example.spirit.template.domain;

public class MusicBean {

    private String title;
    private String duration;
    private String artist;
    private String path;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "MusicBean{" +
                "title='" + title + '\'' +
                ", duration='" + duration + '\'' +
                ", artist='" + artist + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
