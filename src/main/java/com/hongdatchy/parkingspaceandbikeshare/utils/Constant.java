/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 17/06/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.utils;

/**
 * class chứa các hằng số trong hệ thống
 *
 * @author hongdatchy
 */
public class Constant {

    // khoá bí mật của token khi mã hoá
    public static final String TOKEN_SECRET = "hongdatchy";
    // thời gian hết hạn của token
    public static final long EXPIRATION_TIME = 86400000; // 1 day
//    public static final long EXPIRATION_TIME = 60000; // 1 minute
    // chuỗi cộng thêm vào đầu plantext trước khi mã hoá
    public static final String TOKEN_PREFIX = "sanLab";

    public static final String USER_ID_ATTRIBUTE = "userId";

    public static final String MALE_GENDER = "Nam";
    public static final String FEMALE_GENDER = "Nữ";
    public static final String OTHER_GENDER = "Khác";

    public static final String HEADER_ACTIVE_MAIL = "Xác nhận email với tài khoản của hệ thống bike share";

    public static final String REGISTER_RESULT_SUCCESS = "Xin hãy check email của bạn";
    public static final String REGISTER_RESULT_FAIL_1 = "Giới tính không đúng";
    public static final String REGISTER_RESULT_FAIL_2 = "Email đã tồn tại";
    public static final String REGISTER_RESULT_FAIL_3 = "Email không đúng";

    public static final String URL_BROKER_MQTT = "tcp://localhost:1883";
    public static final String USERNAME_MQTT = "hongdatchy";
    public static final String PASSWORD_MQTT = "hongdat10";

    public static final boolean CLOSE_STATUS_DEVICE = false;
    public static final boolean OPEN_STATUS_DEVICE = true;


}
