/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 19/06/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.sevice.impl;

import com.hongdatchy.parkingspaceandbikeshare.entities.model.*;
import com.hongdatchy.parkingspaceandbikeshare.entities.request.RegisterForm;
import com.hongdatchy.parkingspaceandbikeshare.entities.request.RentBikeRequest;
import com.hongdatchy.parkingspaceandbikeshare.entities.response.ContractBikeResponse;
import com.hongdatchy.parkingspaceandbikeshare.mqtt.MqttService;
import com.hongdatchy.parkingspaceandbikeshare.repository.*;
import com.hongdatchy.parkingspaceandbikeshare.sevice.SendEmailService;
import com.hongdatchy.parkingspaceandbikeshare.sevice.UserService;
import com.hongdatchy.parkingspaceandbikeshare.utils.Common;
import com.hongdatchy.parkingspaceandbikeshare.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * class impl UserService
 *
 * @author hongdatchy
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    SendEmailService sendEmailService;

    @Autowired
    ContractBikeRepository contractBikeRepository;

    @Autowired
    MqttService mqttService;

    @Autowired
    PathRepository pathRepository;



    @Override
    public String register(RegisterForm registerForm) {
        String rs;
        if(!Constant.MALE_GENDER.equals(registerForm.getGender())
                && !Constant.FEMALE_GENDER.equals(registerForm.getGender())
                && !Constant.OTHER_GENDER.equals(registerForm.getGender())){
            rs = Constant.REGISTER_RESULT_FAIL_1;
        }else {
            User user = userRepository.findUserByEmail(registerForm.getEmail());
            Admin admin = adminRepository.findAdminByEmail(registerForm.getEmail());
            if(user != null || admin != null){
                rs = Constant.REGISTER_RESULT_FAIL_2;
            }else{
                String activeCode = Common.getRandomCode();
                boolean b = sendEmailService.sendMailHtml(registerForm.getEmail()
                        , Constant.HEADER_ACTIVE_MAIL
                        , activeCode, registerForm);
                if(b) {
                    userRepository.save(User.builder()
                            .id(0)
                            .email(registerForm.getEmail())
                            .password(Common.encodePasswordBySHA256((registerForm.getPassword())))
                            .phone(registerForm.getPhone())
                            .firstname(registerForm.getFirstname())
                            .lastname(registerForm.getLastname())
                            .birthday(registerForm.getBirthday())
                            .cityId(registerForm.getCityId())
                            .districtId(registerForm.getDistrictId())
                            .wardId(registerForm.getWardId())
                            .gender(registerForm.getGender())
                            .isActive(false)
                            .activeCode(activeCode)
                            .build());
                    rs = Constant.REGISTER_RESULT_SUCCESS;
                } else {
                    rs = Constant.REGISTER_RESULT_FAIL_3;
                }
            }
        }
        return rs;
    }

    @Override
    public boolean activeAccount(String code) {
        boolean isSuccess = false;
        User user = userRepository.findUserByActiveCode(code);
        if(user != null && !user.getIsActive()){
            user.setIsActive(true);
            userRepository.save(user);
            isSuccess = true;
        }
        return isSuccess;
    }

    @Override
    public ContractBike rentBike(RentBikeRequest rentBikeRequest, int userId) {
        ContractBike contractBike = null;
        int bikeId = rentBikeRequest.getBikeId();
        // tìm device tương ứng với bike
        Device device = deviceRepository.findByBikeId(bikeId);
        // nếu device đang mở (chưa được thuê)
        if(device != null && device.getStatusLock() == Constant.CLOSE_STATUS_DEVICE){
            contractBike = contractBikeRepository.save(ContractBike.builder()
                    .id(0)
                    .bikeId(rentBikeRequest.getBikeId())
                    .paymentMethod(rentBikeRequest.getPaymentMethod())
                    .startTime(new Date())
                    .userId(userId)
                    .build());

            // đẩy bản tin mqtt thông báo cho thiết bị là hãy mở khoá
            // và đợi bản tin mở khoá từ device gửi về
            mqttService.publish(rentBikeRequest.getBikeId(), "op");
        }
        return contractBike;
    }

    @Override
    public ContractBikeResponse endRentBike(int bikeId, int userId) {

        ContractBikeResponse contractBikeResponse = null;
        List<ContractBike> contractBikeList = contractBikeRepository.findContractsBikeByBikeId(bikeId);
        if(!contractBikeList.isEmpty()){
            ContractBike contractBike = contractBikeList.get(contractBikeList.size()-1);
            if(contractBike.getUserId()== userId){
                contractBike.setEndTime(new Date());
                contractBikeRepository.save(contractBike);
                Path path = pathRepository.findPathsByContractId(contractBike.getId());
                contractBikeResponse = ContractBikeResponse.builder()
                        .id(contractBike.getId())
                        .startTime(contractBike.getStartTime())
                        .endTime(contractBike.getEndTime())
                        .bikeId(contractBike.getBikeId())
                        .paymentMethod(contractBike.getPaymentMethod())
                        .userId(contractBike.getUserId())
                        .distance(path.getDistance())
                        .routes(path.getRoutes())
                        .build();
            }
        }

        return contractBikeResponse;
    }
}
