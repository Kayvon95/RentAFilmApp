package com.example.lars.rentafilmapplication.Domain;

import java.io.Serializable;

/**
 * Created by Kayvon Rahimi on 17-6-2017.
 */

public class Customer implements Serializable {

    private int customerId;
    private int storeId;
    private String firstName;
    private String lastName;
    private String address;
    private boolean activeState;
    private String creationDate;
    private String lastDate;
    private String password;

    public Customer(int customerId, int storeId, String firstName, String lastName, String address, boolean activeState, String creationDate, String lastDate, String password) {
        this.customerId = customerId;
        this.storeId = storeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.activeState = activeState;
        this.creationDate = creationDate;
        this.lastDate = lastDate;
        this.password = password;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
