package com.example.movieinfo.model;

import com.google.gson.annotations.SerializedName;

public class MovieResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("status_message")
    private String status_message;

    @SerializedName("data")
    private  MovieData movieData;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public MovieData getMovieData() {
        return movieData;
    }

    public void setMovieData(MovieData movieData) {
        this.movieData = movieData;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "status='" + status + '\'' +
                ", status_message='" + status_message + '\'' +
                ", movieData=" + movieData +
                '}';
    }
}
