package com.hongdatchy.parkingspaceandbikeshare.repository;

import com.hongdatchy.parkingspaceandbikeshare.entities.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    Device findByBikeId(int bikeId);
}