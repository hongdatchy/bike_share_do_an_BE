/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 17/06/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.sevice;

import com.hongdatchy.parkingspaceandbikeshare.entities.request.RegisterForm;

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
}
