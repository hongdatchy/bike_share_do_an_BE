/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 05/07/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.zDemoDevice;

import com.hongdatchy.parkingspaceandbikeshare.sevice.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * addition for demo device
 *
 * @author hongdatchy
 */
@RequestMapping("/api/common/")
@RestController
public class DeviceControllerDemo {

    @Autowired
    BikeService bikeService;

    @Autowired
    MqttServiceDemo mqttServiceDemo;
    
    @GetMapping("findAllBikeInfoForDeMo")
    public ResponseEntity<Object> findAllDeviceDeMo() {
        return ResponseEntity.ok(bikeService.findAllBikeInfo());
    }

    @GetMapping("pushLatLngToServerByMqtt/{bikeId}")
    public void pushLatLngToServerByMqtt(@PathVariable int bikeId) throws InterruptedException {
        List<String> messList = ConstantDemo.messList;
        for (String mess: messList) {
            mqttServiceDemo.publish(bikeId, "p," + mess);
            Thread.sleep(3000);
        }
    }

    @GetMapping("closeLockToServerByMqtt/{bikeId}")
    public void closeLockToServerByMqtt(@PathVariable int bikeId) {
        mqttServiceDemo.publish(bikeId, "cl");
    }
}
