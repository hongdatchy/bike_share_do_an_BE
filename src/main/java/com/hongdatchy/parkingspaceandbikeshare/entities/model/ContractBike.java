package com.hongdatchy.parkingspaceandbikeshare.entities.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "contract_bike")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+7")
    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+7")
    @Column(name = "end_time")
    private Date endTime;

}
