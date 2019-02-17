package com.example.movieinfo.model;

import com.google.gson.annotations.SerializedName;

public class MovieTorrents {
    @SerializedName("url")
    private String url;

    @SerializedName("hash")
    private String hash;

    @SerializedName("quality")
    private String quality;

    @SerializedName("bluray")
    private String bluray;

    @SerializedName("seeds")
    private Integer seeds;

    @SerializedName("peers")
    private Integer peers;

    @SerializedName("size")
    private String size;

    @SerializedName("size_bytes")
    private String size_bytes;

    @SerializedName("date_uploaded")
    private String date_uploaded;

    @SerializedName("date_uploaded_unix")
    private String date_uploaded_unix;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getBluray() {
        return bluray;
    }

    public void setBluray(String bluray) {
        this.bluray = bluray;
    }

    public Integer getSeeds() {
        return seeds;
    }

    public void setSeeds(Integer seeds) {
        this.seeds = seeds;
    }

    public Integer getPeers() {
        return peers;
    }

    public void setPeers(Integer peers) {
        this.peers = peers;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize_bytes() {
        return size_bytes;
    }

    public void setSize_bytes(String size_bytes) {
        this.size_bytes = size_bytes;
    }

    public String getDate_uploaded() {
        return date_uploaded;
    }

    public void setDate_uploaded(String date_uploaded) {
        this.date_uploaded = date_uploaded;
    }

    public String getDate_uploaded_unix() {
        return date_uploaded_unix;
    }

    public void setDate_uploaded_unix(String date_uploaded_unix) {
        this.date_uploaded_unix = date_uploaded_unix;
    }

    @Override
    public String toString() {
        return "MovieTorrents{" +
                "url='" + url + '\'' +
                ", hash='" + hash + '\'' +
                ", quality='" + quality + '\'' +
                ", bluray='" + bluray + '\'' +
                ", seeds=" + seeds +
                ", peers=" + peers +
                ", size='" + size + '\'' +
                ", size_bytes=" + size_bytes +
                ", date_uploaded='" + date_uploaded + '\'' +
                ", date_uploaded_unix='" + date_uploaded_unix + '\'' +
                '}';
    }
}
