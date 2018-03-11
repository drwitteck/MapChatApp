package com.example.derekwitteck.mapchatapp;

public class Partner {
    private String username, latitude, longitude;
//    Double latitude, longitude;

//    public Partner(String username, Double latitude, Double longitude){
//        this.username = username;
//        this.latitude = latitude;
//        this.longitude = longitude;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
