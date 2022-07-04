/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 18/06/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.entities.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * class chứa email và password trong request khi login
 *
 * @author hongdatchy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {
    @NotNull
    String email;
    @NotNull
    String password;
}
