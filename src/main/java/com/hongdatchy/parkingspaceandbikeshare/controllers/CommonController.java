/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 18/06/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.controllers;

import com.hongdatchy.parkingspaceandbikeshare.entities.model.*;
import com.hongdatchy.parkingspaceandbikeshare.entities.request.LoginForm;
import com.hongdatchy.parkingspaceandbikeshare.entities.request.RegisterForm;
import com.hongdatchy.parkingspaceandbikeshare.entities.response.BikeInfo;
import com.hongdatchy.parkingspaceandbikeshare.entities.response.LoginResponse;
import com.hongdatchy.parkingspaceandbikeshare.entities.response.MyResponse;
import com.hongdatchy.parkingspaceandbikeshare.entities.response.UserResponse;
import com.hongdatchy.parkingspaceandbikeshare.repository.*;
import com.hongdatchy.parkingspaceandbikeshare.sevice.BikeService;
import com.hongdatchy.parkingspaceandbikeshare.sevice.UserService;
import com.hongdatchy.parkingspaceandbikeshare.utils.Common;
import com.hongdatchy.parkingspaceandbikeshare.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * class chứa các api không cần đăng nhập
 *
 * @author hongdatchy
 */
@RequestMapping("api/common/")
@RestController
public class CommonController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    BlackListRepository blackListRepository;

    @Autowired
    UserService userService;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    WardRepository wardRepository;

    @Autowired
    StationRepository stationRepository;

    @Autowired
    BikeService bikeService;


    @PostMapping("loginUser")
    ResponseEntity<Object> loginUser(@RequestBody LoginForm loginForm){
        User user = userRepository.findUserByEmail(loginForm.getEmail());
        UserResponse userResponse = null;
        if(user != null){
            String encodePass = Common.encodePasswordBySHA256(loginForm.getPassword());
            if(encodePass.equals(user.getPassword())){
                userResponse = UserResponse.builder()
                        .birthday(user.getBirthday())
                        .cityId(user.getCityId())
                        .creditCard(user.getCreditCard())
                        .districtId(user.getDistrictId())
                        .email(user.getEmail())
                        .firstname(user.getFirstname())
                        .gender(user.getGender())
                        .idNumber(user.getIdNumber())
                        .image(user.getImage())
                        .lastname(user.getLastname())
                        .phone(user.getPhone())
                        .wardId(user.getWardId())
                        .build();
            }
        }
        return ResponseEntity.ok(userResponse != null ?
                MyResponse.success(new LoginResponse(Common.encodeEmail(loginForm.getEmail()), userResponse))
                : MyResponse.fail("username or password is incorrect"));
    }

    @PostMapping("loginAd")
    ResponseEntity<Object> loginMnAd(@RequestBody LoginForm loginForm){
        Admin admin = adminRepository.findAdminByEmail(loginForm.getEmail());
        if(admin != null){
            String encodePass = Common.encodePasswordBySHA256(loginForm.getPassword());
            if(encodePass.equals(admin.getPassword())){
                return ResponseEntity.ok(MyResponse.success(Common.encodeEmail(loginForm.getEmail())));
            }
        }
        return ResponseEntity.ok(MyResponse.fail("username or password is incorrect"));
    }

    @PostMapping("logout")
    ResponseEntity<Object> logout(@RequestBody String token){
        String email = Common.decodeToken(token);
        boolean rs = false;
        if(userRepository.findUserByEmail(email) != null
                || adminRepository.findAdminByEmail(email) != null){
            blackListRepository.save(new BlackList(0, token));
            rs = true;
        }
        return ResponseEntity.ok(MyResponse.success(rs));
    }

    @PostMapping("register")
    public ResponseEntity<Object> register(@RequestBody RegisterForm registerForm) {

        String rsRegister = userService.register(registerForm);
        return rsRegister.equals(Constant.REGISTER_RESULT_SUCCESS) ?
                ResponseEntity.ok(MyResponse.success(rsRegister)):
                ResponseEntity.ok(MyResponse.fail(rsRegister));
    }

    @GetMapping("active/{code}")
    public ResponseEntity<Object> active(@PathVariable String code) {
        return userService.activeAccount(code) ? ResponseEntity.ok(MyResponse.success("active success")) :
                ResponseEntity.ok(MyResponse.fail("active fail"));
    }

    @GetMapping("city")
    public ResponseEntity<Object> findAllCity() {
        return ResponseEntity.ok(MyResponse.success(cityRepository.findAll()));
    }

    @GetMapping("district/{cityId}")
    public ResponseEntity<Object> findDistrictByCityId(@PathVariable int cityId) {
        return ResponseEntity.ok(MyResponse.success(districtRepository.findByCityId(cityId)));
    }

    @GetMapping("ward/{districtId}")
    public ResponseEntity<Object> findWardByDistrictId(@PathVariable int districtId) {
        return ResponseEntity.ok(MyResponse.success(wardRepository.findByDistrictId(districtId)));
    }

    @GetMapping("station")
    public ResponseEntity<Object> findAllStation() {
        return ResponseEntity.ok(MyResponse.success(stationRepository.findAll()));
    }

    @GetMapping("bike/{bikeId}")
    public ResponseEntity<Object> findBikeInfoByBikeId(@PathVariable int bikeId) {
        BikeInfo bikeInfo = bikeService.getBikeInfoByBikeId(bikeId);
        return ResponseEntity.ok(bikeInfo!= null ?
                MyResponse.success(bikeInfo) :
                MyResponse.fail("bikeId is incorrect"));
    }
}
