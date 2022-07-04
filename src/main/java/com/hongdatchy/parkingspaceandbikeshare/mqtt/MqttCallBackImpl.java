package com.hongdatchy.parkingspaceandbikeshare.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hongdatchy.parkingspaceandbikeshare.entities.model.ContractBike;
import com.hongdatchy.parkingspaceandbikeshare.entities.model.Device;
import com.hongdatchy.parkingspaceandbikeshare.repository.ContractBikeRepository;
import com.hongdatchy.parkingspaceandbikeshare.repository.DeviceRepository;
import com.hongdatchy.parkingspaceandbikeshare.sevice.PathService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Component
public class MqttCallBackImpl implements MqttCallback {

    @Autowired
    PathService pathService;

    @Autowired
    ContractBikeRepository contractBikeRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    private SimpMessagingTemplate template;

    public void connectionLost(Throwable cause) {
        System.out.println("disconnect");
    }

    /**
     *
     * @param topic topic
     * @param message mqtt message (message from device to server)
     *      update path and lat, long of device : p,HHmmss,lat,lon (position)
     *      open device : cl (close)
     */
    public void messageArrived(String topic, MqttMessage message)  {
        // The messages obtained after subscribe will be executed here
        System.out.println("Received message topic:" + topic);
//        System.out.println("Received message Qos:" + message.getQos());
//        System.out.println("Received message content:" + new String(message.getPayload()));

        String[] mess = new String(message.getPayload()).split(",");
        System.out.println(Arrays.toString(mess));
        String[] topicSplit = topic.split("/");
        int bikeId = Integer.parseInt(topicSplit[topicSplit.length-1]);
        try {

            List<ContractBike> contracts = contractBikeRepository.findContractsBikeByUserId(bikeId);
            if(mess[0].equals("p")) {
                Device device = deviceRepository.findByBikeId(bikeId);
                double latitude = Double.parseDouble(mess[2]);
                double longitude = Double.parseDouble(mess[3]);

                if(device.getStatusLock()){ // true --> xe đang được thuê
                    if(contracts.size() > 0){
                        // update time end to contract
                        ContractBike contract = contracts.get(contracts.size()-1);
                        String timeGtm0 = mess[1];
                        SimpleDateFormat f = new SimpleDateFormat("ddMMyyyyHHmmss");
                        f.setTimeZone(TimeZone.getTimeZone("GMT+0"));
                        // vì mqtt đang gửi lên gmt+0
                        Date date = f.parse(new SimpleDateFormat("ddMMyyyy").format(new Date())+ timeGtm0);
                        contract.setEndTime(new Timestamp(date.getTime()));
                        contractBikeRepository.save(contract);

                        // update path
                        pathService.updatePathFormGPS(contract.getId(), bikeId, latitude, longitude);
                    }
                }
                // luôn cập nhật cho device
                device.setLatitude(latitude);
                device.setLongitude(longitude);
                deviceRepository.save(device);


            } else if (mess[0].equals("cl")) {
                Device device = deviceRepository.findByBikeId(bikeId);
                if(device != null){
                    device.setStatusLock(false);
                    deviceRepository.save(device);
                }

                // thông báo cho android hãy đóng khoá
                template.convertAndSend("/topic/greetings/" + bikeId, "cl");
            }
        } catch (ParseException | JsonProcessingException e){
            e.printStackTrace();
        }
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }
}
