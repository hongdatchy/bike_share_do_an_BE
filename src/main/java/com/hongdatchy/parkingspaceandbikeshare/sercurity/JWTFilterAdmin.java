package com.hongdatchy.parkingspaceandbikeshare.sercurity;


import com.hongdatchy.parkingspaceandbikeshare.entities.model.Admin;
import com.hongdatchy.parkingspaceandbikeshare.repository.AdminRepository;
import com.hongdatchy.parkingspaceandbikeshare.repository.BlackListRepository;
import com.hongdatchy.parkingspaceandbikeshare.utils.Common;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@AllArgsConstructor
public class JWTFilterAdmin implements Filter {
    private final AdminRepository adminRepo;
    private final BlackListRepository blackListRepo;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getHeader("token");
        if (token != null) {
            String email = Common.decodeToken(token);
            if (email != null){
                Admin admin = adminRepo.findAdminByEmail(email);
                if(admin != null && blackListRepo.findBlackListByToken(token) == null){
                    filterChain.doFilter(request, response);
                }
            }
        }
        response.setStatus(401);
    }
}