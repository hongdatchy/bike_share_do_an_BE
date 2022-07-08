/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 17/06/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import static com.hongdatchy.parkingspaceandbikeshare.utils.Constant.*;


/**
 * class chứa các hàm dùng chung trong cả hệ thống
 *
 * @author hongdatchy
 */
public class Common {

    /**
     *
     * hàm mã hoá password bằng hàm băm SHA-256
     *
     * @param password password cần mã hoá
     * @return password đã được mã hoá bằng hàm băm SHA-256
     * null nếu có lỗi
     */
    public static String encodePasswordBySHA256(String password) {
        String rs = null;
        try {
            if(password != null){
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    md.update(password.getBytes());
                    rs = bytesToHex(md.digest());

            }
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    /**
     * convert bytes thành Hex
     *
     * @param bytes bytes cần convert
     * @return hex sau khi đã convert
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return result.toString();
    }

    /**
     * mã hoá email thành token
     *
     * @param email email cần mã hoá
     * @return token tương ứng với email sau khi đã mã hoá
     */
    public static String encodeEmail(String email) {
        return TOKEN_PREFIX + JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(TOKEN_SECRET.getBytes()));

    }

    /**
     * giải mã hoá token thành email
     *
     * @param token token cần giải mã hoá
     * @return email sau khi đã giải mã hoá
     */
    public static String decodeToken(String token) {
        String email = null;
        try{
            if(!"".equals(token)){
                email= JWT.require(Algorithm.HMAC512(TOKEN_SECRET.getBytes()))
                        .build().verify(token.replace(TOKEN_PREFIX, ""))
                        .getSubject();
            }
        }catch (JWTVerificationException e){
            System.out.println("");
        }

        return email;
    }

    /**
     * hàm tạo 1 mã code không trùng lặp để người dùng có thể active tài khoản sau khi đã đăng kí
     *
     * @return mã active code cho người dùng
     */
    public static String getRandomCode(){
        StringBuilder rs= new StringBuilder();
        for (int i=0; i< 4; i++){
            rs.append((int) (Math.random() * 10));
        }
        return rs.toString() + new Date().getTime()/1000;
    }

    /**
     * tính khoảng các 2 điểm A và B
     *
     * @param lat1 lat điểm A
     * @param lng1 long điểm A
     * @param lat2 lat điểm B
     * @param lng2 long điểm B
     * @return khoảng cách 2 điểm
     */
    public static double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return earthRadius * c;
    }
}
