package com.hongdatchy.parkingspaceandbikeshare.entities.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "contract_bike")
public class ContractBike {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "bike_id", nullable = false)
    private Integer bikeId;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

}
