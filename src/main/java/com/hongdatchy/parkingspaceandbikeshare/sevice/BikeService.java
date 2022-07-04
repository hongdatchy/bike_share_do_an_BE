/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 17/06/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.sevice;

import com.hongdatchy.parkingspaceandbikeshare.entities.response.BikeInfo;

/**
 *
 *
 * @author hongdatchy
 */
public interface BikeService {
    BikeInfo getBikeInfoByBikeId(int bikeId);
}
