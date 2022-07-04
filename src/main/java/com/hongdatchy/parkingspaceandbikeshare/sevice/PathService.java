/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 17/06/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.sevice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hongdatchy.parkingspaceandbikeshare.entities.model.ContractBike;
import com.hongdatchy.parkingspaceandbikeshare.entities.model.Path;

/**
 *
 *
 * @author hongdatchy
 */
public interface PathService {
    Path updatePathFormGPS(int contractId, int bikeId, double latitude, double longitude) throws JsonProcessingException;
}
