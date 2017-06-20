package com.example.lars.rentafilmapplication.Domain;

import java.io.Serializable;

/**
 * Created by Kayvon Rahimi on 17-6-2017.
 */

public class Film implements Serializable  {
    private int filmId;
    private String title;
    private String description;
    private int releaseYear;
    private int langId;
    private int rentalDuration;
    private int length;
    private String rating;
    private String features;
    private String lastUpdate;

    public Film(int filmId, String title, String description, int releaseYear, int langId, int rentalDuration,
                int length, String rating, String features, String lastUpdate){
        this.filmId = filmId;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.langId = langId;
        this.rentalDuration = rentalDuration;
        this.length = length;
        this.rating = rating;
        this.features = features;
        this.lastUpdate = lastUpdate;
    }

    public String toString(){
        return "Film{" +
                "film_id='" + filmId + '\'' +
                "title='" + title + '\'' +
                "description='" + description + '\'' +
                "release_year='" + releaseYear + '\'' +
                "lang_id='" + langId + '\'' +
                "rental_duration='" + rentalDuration + '\'' +
                "length='" + length + '\'' +
                "rating='" + rating + '\'' +
                "features='" + features + '\'' +
                "last_update='" + lastUpdate + '\'' +
                '}';
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public int getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(int rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}