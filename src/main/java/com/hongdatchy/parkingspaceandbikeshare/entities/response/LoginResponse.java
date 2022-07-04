/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 23/06/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.entities.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * class chưa response khi client login thành công
 *
 * @author hongdatchy
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginResponse {
    String token;
    UserResponse userResponse;
}
