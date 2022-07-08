package com.hongdatchy.parkingspaceandbikeshare.zDemoDevice;


import com.hongdatchy.parkingspaceandbikeshare.utils.Constant;
import lombok.NonNull;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MqttServiceImplDemo implements MqttServiceDemo {
    String broker = Constant.URL_BROKER_MQTT;
    String clientId = MqttAsyncClient.generateClientId();
    MqttClient client;

//    @Autowired
//
//    MqttCallback mqttCallback;

    private final MqttCallback mqttCallback;
    public MqttServiceImplDemo(@Qualifier(value = "MqttCallBackImplDemo") @NonNull MqttCallback mqttCallback) {
        this.mqttCallback = mqttCallback;
    }

    @PostConstruct
    public void innit(){
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(Constant.USERNAME_MQTT);
            connOpts.setPassword(Constant.PASSWORD_MQTT.toCharArray());
            // retain session
            connOpts.setCleanSession(true);
            client.connect(connOpts);
        }catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void publish(int bikeId, String mess) {
        String pubTopic = "upstream/"+ bikeId;
        int qos = 2;
        MqttMessage message = new MqttMessage(mess.getBytes());
        message.setQos(qos);
        try {
            client.publish(pubTopic, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribe(int bikeId) {
        String subTopic = "downstream/"+ bikeId;

        try {
            // set callback
            client.setCallback(mqttCallback);
            // Subscribe
            client.subscribe(subTopic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribeAll() {
        String subTopic = "downstream/#";

        try {
            // set callback
            client.setCallback(mqttCallback);
            // Subscribe
            client.subscribe(subTopic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
