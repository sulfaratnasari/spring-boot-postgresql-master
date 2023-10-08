package com.demo.postgres.entity;

public class LocationDTO {
    private String address;
    private double latitude;
    private double longitude;
    private int total_lots;
    private int lots_available;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getTotal_lots() {
        return total_lots;
    }

    public void setTotal_lots(int total_lots) {
        this.total_lots = total_lots;
    }

    public int getLots_available() {
        return lots_available;
    }

    public void setLots_available(int lots_available) {
        this.lots_available = lots_available;
    }

    
}
