package com.example.lars.rentafilmapplication.Domain;

import java.io.Serializable;

/**
 * Created by Kayvon Rahimi on 17-6-2017.
 */

public class Customer implements Serializable {

    private boolean activeState;
    private String creationDate;
    private String lastDate;
    private String password;
    private String email;

    public Customer(boolean activeState, String creationDate, String lastDate, String password, String email) {

        this.activeState = activeState;
        this.creationDate = creationDate;
        this.lastDate = lastDate;
        this.password = password;
        this.email = email;
    }

    public boolean isActiveState() {
        return activeState;
    }

    public void setActiveState(boolean activeState) {
        this.activeState = activeState;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
