package com.hongdatchy.parkingspaceandbikeshare.repository;

import com.hongdatchy.parkingspaceandbikeshare.entities.model.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListRepository extends JpaRepository<BlackList, Integer> {
    BlackList findBlackListByToken(String token);
}