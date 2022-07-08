package com.hongdatchy.parkingspaceandbikeshare.zDemoDevice;

import lombok.NonNull;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component("MqttCallBackImplDemo")
public class MqttCallBackImplDemo implements MqttCallback {


    private final MqttServiceDemo mqttServiceDemo;
    public MqttCallBackImplDemo(@NonNull @Lazy MqttServiceDemo mqttServiceDemo) {
        this.mqttServiceDemo = mqttServiceDemo;
    }

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("disconnect demo");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message)  {
        String mess = new String(message.getPayload());
        System.out.println("Received message topic demo: " + topic);
        System.out.println("Received message content demo: " + mess);
        // lấy bikeId từ topic
        String[] topicSplit = topic.split("/");
        int bikeId = Integer.parseInt(topicSplit[topicSplit.length-1]);
        // bản tin yêu cầu mở khoá từ server
        if(mess.equals("op")){
            // gửi bản tin thông báo lên web để hiển thị mở khoá (tương ứng với việc mở khoá thật ở device)
            simpMessagingTemplate.convertAndSend("/topic/messages/" , bikeId + ","+message);
            // sau đó gửi bản tin lên server thông báo đã mở khoá thành công
            mqttServiceDemo.publish(bikeId, "op success");

        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete demo --------- " + token.isComplete());
    }
}
