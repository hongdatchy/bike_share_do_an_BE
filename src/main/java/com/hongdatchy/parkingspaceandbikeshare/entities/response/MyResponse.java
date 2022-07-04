/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 18/06/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.entities.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * class đại diện cho body của response trả về cho client
 *
 * @author hongdatchy
 */
@Data
@AllArgsConstructor
public class MyResponse {

    private String message;
    private Object data;

    public static MyResponse success(Object data){
        return new MyResponse("success", data);
    }

    public static MyResponse fail(Object data){
        return new MyResponse("fail", data);
    }

}
