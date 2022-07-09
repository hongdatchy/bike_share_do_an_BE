package com.hongdatchy.parkingspaceandbikeshare.repository;

import com.hongdatchy.parkingspaceandbikeshare.entities.model.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PathRepository extends JpaRepository<Path, Integer> {
    Path findPathsByContractId(int id);
}