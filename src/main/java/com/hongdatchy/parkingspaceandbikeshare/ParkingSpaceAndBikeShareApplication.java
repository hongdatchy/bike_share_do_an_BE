/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 17/06/2022
 */
package com.hongdatchy.parkingspaceandbikeshare;

import com.hongdatchy.parkingspaceandbikeshare.mqtt.MqttService;
import com.hongdatchy.parkingspaceandbikeshare.repository.AdminRepository;
import com.hongdatchy.parkingspaceandbikeshare.repository.UserRepository;
import com.hongdatchy.parkingspaceandbikeshare.sevice.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

/**
 * this is main class of application
 *
 * @author hongdatchy
 */
@SpringBootApplication
public class ParkingSpaceAndBikeShareApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ParkingSpaceAndBikeShareApplication.class, args);
    }

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SendEmailService sendEmailService;

    @Autowired
    MqttService mqttService;

    @Override
    public void run(String... args) {

        //        set timezone cho backend
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
//        sendEmailService.sendMail("hongdatchy@gmail.com", "test", "cc");
        //        server subscriber to mqtt broker
        mqttService.subscribeAll();
    }

}
