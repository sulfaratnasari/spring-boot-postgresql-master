package com.demo.postgres.entity;

import java.util.*;

public class CarparkAvailability {
    public String carpark_number;
    public List<CarparkInfo> carpark_info;

    public String getCarpark_number() {
        return carpark_number;
    }

    public void setCarpark_number(String carpark_number) {
        this.carpark_number = carpark_number;
    }

    public List<CarparkInfo> getCarpark_info() {
        return carpark_info;
    }

    public void setCarpark_info(List<CarparkInfo> carpark_info) {
        this.carpark_info = carpark_info;
    }

}
