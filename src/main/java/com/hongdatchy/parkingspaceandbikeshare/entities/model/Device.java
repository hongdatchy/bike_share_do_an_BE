package com.hongdatchy.parkingspaceandbikeshare.entities.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "device")
public class Device {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bike_id", nullable = false)
    private Integer bikeId;

    @Column(name = "status_lock")
    private Boolean statusLock;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "battery", nullable = false)
    private Integer battery;

}
