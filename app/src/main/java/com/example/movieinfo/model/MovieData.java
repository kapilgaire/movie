package com.example.movieinfo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieData {
    @SerializedName("movie_count")
    private Integer movieCount;

    @SerializedName("limit")
    private Integer limit;

    @SerializedName("page_number")
    private Integer pageNumber;

    @SerializedName("movies")
    private List<Movie> movieList;

    public Integer getMovieCount() {
        return movieCount;
    }

    public void setMovieCount(Integer movieCount) {
        this.movieCount = movieCount;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @Override
    public String toString() {
        return "MovieData{" +
                "movieCount=" + movieCount +
                ", limit=" + limit +
                ", pageNumber=" + pageNumber +
                ", movieList=" + movieList +
                '}';
    }
}
