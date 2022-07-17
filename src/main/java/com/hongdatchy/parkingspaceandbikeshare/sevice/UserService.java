/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 17/06/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.sevice;

import com.hongdatchy.parkingspaceandbikeshare.entities.model.ContractBike;
import com.hongdatchy.parkingspaceandbikeshare.entities.request.RegisterForm;
import com.hongdatchy.parkingspaceandbikeshare.entities.request.RentBikeRequest;
import com.hongdatchy.parkingspaceandbikeshare.entities.response.ContractBikeResponse;

import java.util.List;

/**
 *
 *
 * @author hongdatchy
 */
public interface UserService {


    /**
     * thực hiện chức năng đăng kí: thêm 1 người dùng mới: với isActive = 0, gửi code active vào email
     *
     * @param registerForm form đăng kí của người dùng, trường gender phải là 1 trong 2 giá trị MALE hoặc FEMALE
     * @return kết quả của hành động đăng kí dưới dạng string
     */
    String register(RegisterForm registerForm);

    /**
     * thực hiện active account dựa vào active code
     *
     * @param code active code của account cần active
     * @return true nếu active code trùng với code truyền vào và account phải là chưa đc active
     * ngược lại return false
     */
    boolean activeAccount(String code);

    /**
     * thự hiện xử lý hành động thuê xe
     *
     * @param rentBikeRequest nội dung bản tin thuê xe gửi lên từ client
     * @param userId id cuả người thuê xe
     * @return ContractBike nếu thuê thành công, null nếu thất bại
     */
    ContractBike rentBike(RentBikeRequest rentBikeRequest, int userId);

    /**
     * thực hiện tiếp tực thuê xe
     *
     * @param bikeId id của bike cần tiếp tuc thuê
     * @param userId id cuả người thuê xe
     * @return true nếu thành công, false nếu thất bại
     */
    boolean continueRentBike(int bikeId, int userId);

    /**
     * thự hiện xử lý hành động kết thúc thuê xe
     *
     * @param bikeId id của bike cần kết thúc thuê
     * @param userId id cuả người thuê xe
     * @return ContractBike nếu kết thúc thành công, null nếu thất bại
     */
    ContractBikeResponse endRentBike(int bikeId, int userId);


}
