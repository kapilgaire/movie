package com.example.movieinfo.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.movieinfo.model.Movie;

import javax.inject.Inject;

public class SelectedMovieViewModel extends ViewModel {
    private final MutableLiveData<Movie> selectedMovie = new MutableLiveData<>();

    @Inject
    public SelectedMovieViewModel() {
    }

    public LiveData<Movie> getSelectedMovie() {
        return selectedMovie;
    }

   public void setSelectedMovie(Movie movie){
        selectedMovie.setValue(movie);
    }
}
