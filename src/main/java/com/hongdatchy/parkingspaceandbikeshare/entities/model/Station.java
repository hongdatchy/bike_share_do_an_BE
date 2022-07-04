package com.hongdatchy.parkingspaceandbikeshare.entities.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "station")
public class Station {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slot_quantity", nullable = false)
    private Integer slotQuantity;

    @Column(name = "current_number_car", nullable = false)
    private Integer currentNumberCar;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;



}
