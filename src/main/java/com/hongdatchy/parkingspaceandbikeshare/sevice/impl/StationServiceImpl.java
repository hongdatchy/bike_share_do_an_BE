/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 15/07/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.sevice.impl;

import com.hongdatchy.parkingspaceandbikeshare.entities.response.StationResponse;
import com.hongdatchy.parkingspaceandbikeshare.repository.BikeRepository;
import com.hongdatchy.parkingspaceandbikeshare.repository.StationRepository;
import com.hongdatchy.parkingspaceandbikeshare.sevice.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 * @author hongdatchy
 */
@Service
public class StationServiceImpl implements StationService {

    @Autowired
    StationRepository stationRepository;

    @Autowired
    BikeRepository bikeRepository;

    @Override
    public List<StationResponse> findAllStation() {
        return stationRepository.findAll().stream().map(station -> {
            return StationResponse.builder()
                    .id(station.getId())
                    .name(station.getName())
                    .longitude(station.getLongitude())
                    .latitude(station.getLatitude())
                    .location(station.getLocation())
                    .slotQuantity(station.getSlotQuantity())
                    .currentNumberCar((int) bikeRepository.findAll().stream().filter(bike -> {
                        return bike.getStationId().equals(station.getId());
                    }).count())
                    .build();
        }).collect(Collectors.toList());
    }
}
