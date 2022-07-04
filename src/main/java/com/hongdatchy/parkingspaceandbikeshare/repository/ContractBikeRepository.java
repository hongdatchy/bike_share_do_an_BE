package com.hongdatchy.parkingspaceandbikeshare.repository;

import com.hongdatchy.parkingspaceandbikeshare.entities.model.ContractBike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractBikeRepository extends JpaRepository<ContractBike, Integer> {
    List<ContractBike> findContractsBikeByUserId(int userId);

}