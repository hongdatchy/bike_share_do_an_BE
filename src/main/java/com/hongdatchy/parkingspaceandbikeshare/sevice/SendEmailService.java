package com.hongdatchy.parkingspaceandbikeshare.sevice;


import com.hongdatchy.parkingspaceandbikeshare.entities.request.RegisterForm;

public interface SendEmailService {
    /**
     * hàm gửi email dưới dạng text
     *
     * @param userMail địa chỉ email người nhận
     * @param header tiêu của email
     * @param content nội dung của email
     * @return true nếu gửi email thành công
     * false nếu thất bại
     */
    boolean sendMail(String userMail, String header, String content);

    /**
     * gửi email dưới dạng html
     *
     * @param userMail địa chỉ email người nhận
     * @param header tiêu đề của email
     * @param activeCode mã active code
     * @param registerForm form đăng kí của người dùng
     * @return true nếu thành công
     * false nếu thất bại
     */
    boolean sendMailHtml(String userMail, String header, String activeCode, RegisterForm registerForm) ;
}
