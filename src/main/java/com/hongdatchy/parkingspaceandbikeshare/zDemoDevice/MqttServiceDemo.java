package com.hongdatchy.parkingspaceandbikeshare.zDemoDevice;

public interface MqttServiceDemo {
    void publish(int bikeId, String mess);
    void subscribe(int bikeId);
    void subscribeAll();
}
