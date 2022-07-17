/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 17/06/2022
 */
package com.hongdatchy.parkingspaceandbikeshare.sevice;

import com.hongdatchy.parkingspaceandbikeshare.entities.response.ContractBikeResponse;

import java.util.List;

/**
 *
 *
 * @author hongdatchy
 */
public interface ContractBikeService {

    /**
     * lấy tất cả các contract của 1 user
     *
     * @param userId id user cần lấy contract
     * @return list các contract, nếu không có trả về list rỗng
     */
    List<ContractBikeResponse> getAllContractUser(int userId);
}
