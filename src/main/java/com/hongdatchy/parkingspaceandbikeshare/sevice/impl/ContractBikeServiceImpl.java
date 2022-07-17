/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 17/07/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.sevice.impl;

import com.hongdatchy.parkingspaceandbikeshare.entities.model.Path;
import com.hongdatchy.parkingspaceandbikeshare.entities.response.ContractBikeResponse;
import com.hongdatchy.parkingspaceandbikeshare.repository.ContractBikeRepository;
import com.hongdatchy.parkingspaceandbikeshare.repository.PathRepository;
import com.hongdatchy.parkingspaceandbikeshare.sevice.ContractBikeService;
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
public class ContractBikeServiceImpl implements ContractBikeService {

    @Autowired
    ContractBikeRepository contractBikeRepository;

    @Autowired
    PathRepository pathRepository;

    @Override
    public List<ContractBikeResponse> getAllContractUser(int userId) {
        return contractBikeRepository.findContractsBikeByUserId(userId).stream().map(
                contractBike -> {
                    Path path = pathRepository.findPathsByContractId(contractBike.getId());
                    return ContractBikeResponse.builder()
                            .id(contractBike.getId())
                            .startTime(contractBike.getStartTime())
                            .endTime(contractBike.getEndTime())
                            .bikeId(contractBike.getBikeId())
                            .paymentMethod(contractBike.getPaymentMethod())
                            .userId(userId)
                            .distance(path != null ? path.getDistance() : null)
                            .routes(path != null ? path.getRoutes(): null)
                            .build();
                }).collect(Collectors.toList());
    }
}
