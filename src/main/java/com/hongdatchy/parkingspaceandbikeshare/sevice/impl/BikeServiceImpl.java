/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 01/07/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.sevice.impl;

import com.hongdatchy.parkingspaceandbikeshare.entities.model.Bike;
import com.hongdatchy.parkingspaceandbikeshare.entities.model.Device;
import com.hongdatchy.parkingspaceandbikeshare.entities.response.BikeInfo;
import com.hongdatchy.parkingspaceandbikeshare.repository.BikeRepository;
import com.hongdatchy.parkingspaceandbikeshare.repository.DeviceRepository;
import com.hongdatchy.parkingspaceandbikeshare.sevice.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 *
 * @author hongdatchy
 */
@Service
public class BikeServiceImpl implements BikeService {

    @Autowired
    BikeRepository bikeRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Override
    public BikeInfo getBikeInfoByBikeId(int bikeId) {
        BikeInfo bikeInfo = null;
        Optional<Bike> bike = bikeRepository.findById(bikeId);
        if(bike.isPresent()){
            Device device = deviceRepository.findByBikeId(bike.get().getId());
            if(device != null){
                bikeInfo = BikeInfo.builder()
                        .id(bikeId)
                        .frameNumber(bike.get().getFrameNumber())
                        .productYear(bike.get().getProductYear())
                        .stationId(bike.get().getStationId())
                        .battery(device.getBattery())
                        .deviceId(device.getId())
                        .latitude(device.getLatitude())
                        .longitude(device.getLongitude())
                        .statusLock(device.getStatusLock())
                        .build();
            }
        }
        return bikeInfo;
    }
}
