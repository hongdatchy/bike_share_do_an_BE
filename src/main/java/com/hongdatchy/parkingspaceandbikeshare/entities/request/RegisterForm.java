package com.hongdatchy.parkingspaceandbikeshare.entities.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RegisterForm {

    private String email;
    private String password;
    private String rePassword;
    private String phone;
    private String firstname;
    private String lastname;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    private Date birthday;
    private String gender;
    private Integer districtId;
    private Integer cityId;
    private Integer wardId;
}
