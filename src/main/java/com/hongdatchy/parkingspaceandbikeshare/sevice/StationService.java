/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 17/06/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.sevice;

import com.hongdatchy.parkingspaceandbikeshare.entities.model.Station;
import com.hongdatchy.parkingspaceandbikeshare.entities.response.StationResponse;

import java.util.List;

/**
 * station service
 *
 * @author hongdatchy
 */
public interface StationService {
    List<StationResponse> findAllStation();
}
