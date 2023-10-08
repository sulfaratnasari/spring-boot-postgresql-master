package com.demo.postgres.entity;

public class CarparkInfoCSV {
    // car_park_no,address,x_coord,y_coord,car_park_type,type_of_parking_system,short_term_parking,free_parking,night_parking,car_park_decks,gantry_height,car_park_basement
    public String car_park_no;
    public String address;
    public double x_coord;
    public double y_coord;

    public double getX_coord() {
        return x_coord;
    }

    public void setX_coord(double x_coord) {
        this.x_coord = x_coord;
    }

    public double getY_coord() {
        return y_coord;
    }

    public void setY_coord(double y_coord) {
        this.y_coord = y_coord;
    }

    public String getCar_park_no() {
        return car_park_no;
    }

    public void setCar_park_no(String car_park_no) {
        this.car_park_no = car_park_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CarparkInfoCSV(String car_park_no, String address, double x_coord, double y_coord) {
        this.car_park_no = car_park_no;
        this.address = address;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
    }
}
