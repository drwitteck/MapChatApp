package com.example.derekwitteck.mapchatapp;

public class Partner {
    private String username;
    private Double latitude, longitude;

    public Partner(/*String username, double latitude, double longitude*/){
//        this.username = username;
//        this.latitude = latitude;
//        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
