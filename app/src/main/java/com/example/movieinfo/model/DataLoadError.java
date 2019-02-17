package com.example.movieinfo.model;

public class DataLoadError {

    private Boolean isError;
    private Throwable throwable;

    public DataLoadError(Boolean isError, Throwable throwable) {
        this.isError = isError;
        this.throwable = throwable;
    }

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
