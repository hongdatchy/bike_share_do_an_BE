/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 17/06/2022
 */
package com.hongdatchy.parkingspaceandbikeshare;

import com.hongdatchy.parkingspaceandbikeshare.mqtt.MqttService;
import com.hongdatchy.parkingspaceandbikeshare.repository.AdminRepository;
import com.hongdatchy.parkingspaceandbikeshare.repository.UserRepository;
import com.hongdatchy.parkingspaceandbikeshare.sevice.SendEmailService;
import com.hongdatchy.parkingspaceandbikeshare.zDemoDevice.MqttServiceDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.SimpMessagingTemplate;

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

    @Autowired
    MqttServiceDemo mqttServiceDemo;

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public void run(String... args) throws InterruptedException {

        //        set timezone cho backend
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));

        //        server subscriber to mqtt broker;
        mqttServiceDemo.subscribeAll();
        mqttService.subscribeAll();

//        while(true){
//            template.convertAndSend("/topic/greetings", "hi");
//            Thread.sleep(5000);
//        }
    }

}
