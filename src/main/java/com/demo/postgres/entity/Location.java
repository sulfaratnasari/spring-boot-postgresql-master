package com.demo.postgres.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "location")
public class Location extends AuditModel {
    @Id
    @GeneratedValue(generator = "location_generator")
    @SequenceGenerator(
            name = "location_generator",
            sequenceName = "location_sequence",
            initialValue = 1
    )
    private Long id;

    @Column(name="carpark_number",columnDefinition = "text")
    private String carparkNumber;

     @Column(name="address",columnDefinition = "text")
    private String address;

    @Column(name="latitude")
    private double latitude;

    @Column(name="longitude")
    private double longitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarparkNumber() {
        return carparkNumber;
    }

    public void setCarparkNumber(String carparkNumber) {
        this.carparkNumber = carparkNumber;
    }

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
    
}
