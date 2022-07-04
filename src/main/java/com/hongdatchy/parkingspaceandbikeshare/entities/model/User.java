package com.hongdatchy.parkingspaceandbikeshare.entities.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "credit_card")
    private String creditCard;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @Column(name = "city_id", nullable = false)
    private Integer cityId;

    @Column(name = "district_id", nullable = false)
    private Integer districtId;

    @Column(name = "ward_id")
    private Integer wardId;

    @Column(name = "last_time_access")
    private Date lastTimeAccess;

    @Column(name = "image")
    private String image;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "reset_code")
    private String resetCode;

    @Column(name = "active_code")
    private String activeCode;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

}
