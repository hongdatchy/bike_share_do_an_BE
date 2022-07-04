package com.hongdatchy.parkingspaceandbikeshare.mqtt;

public interface MqttService {
    void publish(int bikeId, String mess);
    void subscribe(int bikeId);
    void subscribeAll();
}
