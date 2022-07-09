/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 13/04/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.sevice.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hongdatchy.parkingspaceandbikeshare.entities.model.Path;
import com.hongdatchy.parkingspaceandbikeshare.entities.other.Coordinate;
import com.hongdatchy.parkingspaceandbikeshare.repository.ContractBikeRepository;
import com.hongdatchy.parkingspaceandbikeshare.repository.PathRepository;
import com.hongdatchy.parkingspaceandbikeshare.sevice.PathService;
import com.hongdatchy.parkingspaceandbikeshare.utils.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author hongdatchy
 */
@Service
public class PathServiceImpl implements PathService {

    @Autowired
    PathRepository pathRepository;

    @Autowired
    ContractBikeRepository contractBikeRepository;

    /**
     *
     * 1 get all contract with bikeId
     *
     * 2 update path that have contractId equal last contract in list above
     */
    @Override
    public Path updatePathFormGPS(int contractId, int bikeId, double latitude, double longitude) {

        Path path = pathRepository.findPathsByContractId(contractId);
        if (path == null) {
            List<Coordinate> coordinates = new ArrayList<>();
            coordinates.add(new Coordinate(latitude, longitude));
            return pathRepository.save(Path.builder()
                    .id(0)
                    .contractId(contractId)
                    .distance(0.0)
                    .routes(new Gson().toJson(coordinates))
                    .build());
        } else {
            List<Coordinate> coordinates = new Gson().fromJson(path.getRoutes(), new TypeToken<List<Coordinate>>(){}.getType());
            double latPre = coordinates.get(coordinates.size()-1).getLatitude();
            double longPre = coordinates.get(coordinates.size()-1).getLongitude();
            double addDistance = Common.calculateDistance(latPre , longPre , latitude , longitude);
            path.setDistance(path.getDistance() + addDistance);
            coordinates.add(new Coordinate(latitude, longitude));
            path.setRoutes(new Gson().toJson(coordinates));
            return pathRepository.save(path);
        }
    }

}
