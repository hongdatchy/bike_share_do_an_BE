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
    // chuỗi cộng thêm vào đầu plantext trước khi mã hoá
    public static final String TOKEN_PREFIX = "sanLab";

    public static final String USER_ID_ATTRIBUTE = "userId";

    public static final String MALE_GENDER = "Nam";
    public static final String FEMALE_GENDER = "Nữ";
    public static final String OTHER_GENDER = "Khác";

    public static final String HEADER_ACTIVE_MAIL = "Confirmation email account Bike share system activation";

    public static final String REGISTER_RESULT_SUCCESS = "please check your email";
    public static final String REGISTER_RESULT_FAIL_1 = "Gender is incorrect";
    public static final String REGISTER_RESULT_FAIL_2 = "Email was existed";
    public static final String REGISTER_RESULT_FAIL_3 = "Server send email fail";
}
