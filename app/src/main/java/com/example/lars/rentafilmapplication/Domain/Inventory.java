package com.example.lars.rentafilmapplication.Domain;

import java.io.Serializable;

/**
 * Created by Kayvon Rahimi on 17-6-2017.
 */

public class Inventory implements Serializable {
    private int inventoryId;
    private int filmId;
    private int storeId;
    private String lastUpdate;
    private boolean available;

    public Inventory(int inventoryId, int filmId, int storeId, String lastUpdate, boolean available) {
        this.inventoryId = inventoryId;
        this.filmId = filmId;
        this.storeId = storeId;
        this.lastUpdate = lastUpdate;
        this.available = available;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
