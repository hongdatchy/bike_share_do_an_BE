package com.hongdatchy.parkingspaceandbikeshare.entities.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bike")
public class Bike {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "station_id", nullable = false)
    private Integer stationId;

    @Column(name = "frame_number", nullable = false)
    private String frameNumber;

    @Column(name = "product_year", nullable = false)
    private String productYear;

}
