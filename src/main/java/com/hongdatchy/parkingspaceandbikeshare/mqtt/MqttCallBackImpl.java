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



//    /**
//     * nhận bản tin từ device gửi lên và xử lý
//     * có 2 loại bản tin:
//     * Loại 1: bản tin toạ độ và thời gian
//     *  update lat, long của device
//     *  update path nếu xe đang được thuể
//     * Loại 2: bản tin đóng khoá từ device gửi lên server
//     *
//     *
//     * @param topic topic
//     * @param message mqtt message từ device gửi cho server
//     * ---- Loại 1----
//     * p,HHmmss,lat,lon
//     * ví dụ: p,20.333333,106.22221
//     *
//     * ---- Loại 2
//     * cl
//     *
//     */
    @Override
    public void messageArrived(String topic, MqttMessage message)  {
        String[] mess = new String(message.getPayload()).split(",");
        // lấy bikeId từ topic
        String[] topicSplit = topic.split("/");
        int bikeId = Integer.parseInt(topicSplit[topicSplit.length-1]);
        try {
            Device device = deviceRepository.findByBikeId(bikeId);
            switch (mess[0]){
                case "op success":
                    device.setStatusLock(Constant.OPEN_STATUS_DEVICE);
                    deviceRepository.save(device);
                    // thông báo cho android đã mở khoá thành công
                    template.convertAndSend("/topic/openSuccess/" + bikeId, mess[0]);
                    break;
                case "cl success":
                    // gửi bản tin thông báo lên web để hiển thị đóng khoá (tương ứng với việc đóng khoá thật ở device)
                    template.convertAndSend("/topic/messages/" , bikeId + ","+"cl");
                    // ở đây không có break nhé
                case "cl temp":
                    device.setStatusLock(Constant.CLOSE_STATUS_DEVICE);
                    deviceRepository.save(device);
                    // thông báo cho android
                    template.convertAndSend("/topic/updateLatLongOrCheckEndRenting/" + bikeId, mess[0]);
                    break;
                case "op continue success":
                    device.setStatusLock(Constant.OPEN_STATUS_DEVICE);
                    deviceRepository.save(device);
                    // thông báo cho android đã mở khoá để tiếp tục chuyến đi
                    template.convertAndSend("/topic/updateLatLongOrCheckEndRenting/" + bikeId,mess[0]);
                    break;
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
            }

        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        if(!token.isComplete()){
            System.out.println("Gửi bản tin MQTT thất bại");
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("disconnect MQTT");
    }
}
