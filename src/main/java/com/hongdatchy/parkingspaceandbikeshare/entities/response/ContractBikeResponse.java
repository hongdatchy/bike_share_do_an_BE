/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 09/07/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.entities.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 *
 * @author hongdatchy
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractBikeResponse {

    private Integer id;

    private Integer userId;

    private Integer bikeId;

    private String paymentMethod;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+7")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+7")
    private Date endTime;

    private Double distance;

    private String routes;

}
