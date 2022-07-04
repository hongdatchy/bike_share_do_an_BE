package com.hongdatchy.parkingspaceandbikeshare.sercurity;


import com.hongdatchy.parkingspaceandbikeshare.repository.AdminRepository;
import com.hongdatchy.parkingspaceandbikeshare.repository.BlackListRepository;
import com.hongdatchy.parkingspaceandbikeshare.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableWebMvc
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final BlackListRepository blackListRepository;


    @Bean
    public FilterRegistrationBean<JWTFilterUser> jwtFilterUser() {
        FilterRegistrationBean<JWTFilterUser> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JWTFilterUser(userRepository, blackListRepository));
        registrationBean.addUrlPatterns("/api/us/*");
        return registrationBean;
    }



    @Bean
    public FilterRegistrationBean<JWTFilterAdmin> jwtFilterAdmin() {
        FilterRegistrationBean<JWTFilterAdmin> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JWTFilterAdmin(adminRepository, blackListRepository));
        registrationBean.addUrlPatterns("/api/ad/*");
        return registrationBean;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        //stateless
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

}
