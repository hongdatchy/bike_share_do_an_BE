/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 05/07/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.controllers;

import com.hongdatchy.parkingspaceandbikeshare.entities.response.MyResponse;
import com.hongdatchy.parkingspaceandbikeshare.repository.ContractBikeRepository;
import com.hongdatchy.parkingspaceandbikeshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author hongdatchy
 */
@RestController
@RequestMapping("/api/ad/")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContractBikeRepository contractBikeRepository;

    @GetMapping("user")
    ResponseEntity<Object> findAllUser(){
        return ResponseEntity.ok(MyResponse.success(userRepository.findAll()));
    }

    @GetMapping("contract")
    ResponseEntity<Object> findAllContract(){
        return ResponseEntity.ok(MyResponse.success(contractBikeRepository.findAll()));
    }

}
