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
    private int originalLangId;
    private int rentalDuration;
    private int length;
    private Double replacementCost;
    private String rating;
    private String features;
    private String lastUpdate;

    public Film(int filmId, String title, String description, int releaseYear, int langId, int originalLangId, int rentalDuration, int length, Double replacementCost, String rating, String features, String lastUpdate) {
        this.filmId = filmId;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.langId = langId;
        this.originalLangId = originalLangId;
        this.rentalDuration = rentalDuration;
        this.length = length;
        this.replacementCost = replacementCost;
        this.rating = rating;
        this.features = features;
        this.lastUpdate = lastUpdate;
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

    public int getOriginalLangId() {
        return originalLangId;
    }

    public void setOriginalLangId(int originalLangId) {
        this.originalLangId = originalLangId;
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

    public Double getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(Double replacementCost) {
        this.replacementCost = replacementCost;
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