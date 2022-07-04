package com.hongdatchy.parkingspaceandbikeshare.sercurity;


import com.hongdatchy.parkingspaceandbikeshare.entities.model.User;
import com.hongdatchy.parkingspaceandbikeshare.repository.BlackListRepository;
import com.hongdatchy.parkingspaceandbikeshare.repository.UserRepository;
import com.hongdatchy.parkingspaceandbikeshare.utils.Common;
import com.hongdatchy.parkingspaceandbikeshare.utils.Constant;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@AllArgsConstructor
public class JWTFilterUser implements Filter {
    private final UserRepository userRepository;
    private final BlackListRepository blackListRepo;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getHeader("token");
        if (token != null) {
            String email = Common.decodeToken(token);
            if (email != null){
                User user = userRepository.findUserByEmail(email);
                if(user != null && blackListRepo.findBlackListByToken(token) == null){
                    request.setAttribute(Constant.USER_ID_ATTRIBUTE, user.getId());
                    filterChain.doFilter(request, response);
                }
            }
        }
        response.setStatus(401);
    }
}
