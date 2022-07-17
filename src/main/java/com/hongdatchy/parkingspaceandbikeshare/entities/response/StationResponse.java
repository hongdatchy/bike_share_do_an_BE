/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 15/07/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.entities.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author hongdatchy
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StationResponse {

    private Integer id;

    private String name;

    private Integer slotQuantity;

    private Integer currentNumberCar;

    private String location;

    private Double latitude;

    private Double longitude;

}
