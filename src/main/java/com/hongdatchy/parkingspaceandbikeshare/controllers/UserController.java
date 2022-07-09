/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 19/06/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.controllers;

import com.hongdatchy.parkingspaceandbikeshare.entities.model.ContractBike;
import com.hongdatchy.parkingspaceandbikeshare.entities.request.RentBikeRequest;
import com.hongdatchy.parkingspaceandbikeshare.entities.response.ContractBikeResponse;
import com.hongdatchy.parkingspaceandbikeshare.entities.response.MyResponse;
import com.hongdatchy.parkingspaceandbikeshare.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * controller cung cấp các API cho user
 *
 * @author hongdatchy
 */
@RestController
@RequestMapping("/api/us/")
public class  UserController {

    @Autowired
    UserService userService;

    /**
     * ở Mobile, call api này thành công và nhận thông báo đã mở khoá thì mới tính
     * là thành công và hiển thị màn hình thuê xe
     *
     * @param rentBikeRequest thông tin thuể xe
     * @param userId id của người thuể
     * @return contract nếu thành công
     */
    @PostMapping("rentBike")
    public ResponseEntity<Object> rentBike(@RequestBody RentBikeRequest rentBikeRequest, @RequestAttribute Integer userId) {

        ContractBike contractBike = userService.rentBike(rentBikeRequest, userId);
        return ResponseEntity.ok(contractBike != null ?
                MyResponse.success("rent bike success") :
                MyResponse.fail("rent bike fail"));
    }

    @PostMapping("endRentBike")
    public ResponseEntity<Object> endRentBike(@RequestBody int bikeId,@RequestAttribute Integer userId) {
        System.out.println("end rent");
        ContractBikeResponse contractBikeResponse = userService.endRentBike(bikeId, userId);
        return ResponseEntity.ok(contractBikeResponse != null ?
                MyResponse.success(contractBikeResponse) :
                MyResponse.fail("end rent bike fail"));
    }
}
