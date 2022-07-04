/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 23/06/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.entities.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * chưa các thông tin của user cần gửi cho client sau khi login
 *
 * @author hongdatchy
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserResponse {
    private String email;

    private String idNumber;

    private String phone;

    private String firstname;

    private String lastname;

    private String creditCard;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "GMT+7")
    private Date birthday;

    private Integer cityId;

    private Integer districtId;

    private Integer wardId;

    private String image;

    private String gender;

}
