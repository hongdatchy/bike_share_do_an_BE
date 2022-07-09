package com.hongdatchy.parkingspaceandbikeshare.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hongdatchy.parkingspaceandbikeshare.entities.model.ContractBike;
import com.hongdatchy.parkingspaceandbikeshare.entities.model.Device;
import com.hongdatchy.parkingspaceandbikeshare.repository.ContractBikeRepository;
import com.hongdatchy.parkingspaceandbikeshare.repository.DeviceRepository;
import com.hongdatchy.parkingspaceandbikeshare.sevice.PathService;
import com.hongdatchy.parkingspaceandbikeshare.utils.Constant;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("MqttCallBackImpl")
public class MqttCallBackImpl implements MqttCallback {

    @Autowired
    PathService pathService;

    @Autowired
    ContractBikeRepository contractBikeRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("disconnect");
    }

    /**
     * nhận bản tin từ device gửi lên và xử lý
     * có 2 loại bản tin:
     * Loại 1: bản tin toạ độ và thời gian
     *  update lat, long của device
     *  update path nếu xe đang được thuể
     * Loại 2: bản tin đóng khoá từ device gửi lên server
     *
     *
     * @param topic topic
     * @param message mqtt message từ device gửi cho server
     * ---- Loại 1----
     * p,HHmmss,lat,lon
     * ví dụ: p,20.333333,106.22221
     *
     * ---- Loại 2
     * cl
     *
     */
    @Override
    public void messageArrived(String topic, MqttMessage message)  {
        System.out.println("Received message topic: " + topic);
        System.out.println("Received message content: " + new String(message.getPayload()));

        String[] mess = new String(message.getPayload()).split(",");
        // lấy bikeId từ topic
        String[] topicSplit = topic.split("/");
        int bikeId = Integer.parseInt(topicSplit[topicSplit.length-1]);
        try {

            Device device = deviceRepository.findByBikeId(bikeId);
            switch (mess[0]){
                case "p":
                    // lấy ra list contract của user này dựa vào
                    List<ContractBike> contracts = contractBikeRepository.findContractsBikeByBikeId(bikeId);
                    double latitude = Double.parseDouble(mess[1]);
                    double longitude = Double.parseDouble(mess[2]);

                    if(device.getStatusLock() == Constant.OPEN_STATUS_DEVICE){ // xe đang được thuê
                        if(contracts.size() > 0){
                            // get last contract
                            ContractBike contract = contracts.get(contracts.size()-1);
                            // update path

                            pathService.updatePathFormGPS(contract.getId(), bikeId, latitude, longitude);
                            template.convertAndSend("/topic/updateLatLongOrCheckEndRenting/" + bikeId,
                                    latitude + "," + longitude);
                        }
                    }
                    // luôn cập nhật cho device
                    device.setLatitude(latitude);
                    device.setLongitude(longitude);
                    deviceRepository.save(device);
                    break;
                case "cl":
                    System.out.println(device);
                    device.setStatusLock(Constant.CLOSE_STATUS_DEVICE);
                    System.out.println(device);
                    deviceRepository.save(device);
                    // thông báo cho android đã hoàn thành chuyến đi
                    template.convertAndSend("/topic/updateLatLongOrCheckEndRenting/" + bikeId,
                            "Bạn có muốn kết thúc chuyến đi không");
                    break;
                case "op success":
                    device.setStatusLock(Constant.OPEN_STATUS_DEVICE);
                    deviceRepository.save(device);

                    // thông báo cho android đã hoàn thành chuyến đi
                    template.convertAndSend("/topic/notifyRenting/" + bikeId,
                            "Đã mở khoá xe thành công");
                    break;
            }

        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete --------- " + token.isComplete());
    }
}
