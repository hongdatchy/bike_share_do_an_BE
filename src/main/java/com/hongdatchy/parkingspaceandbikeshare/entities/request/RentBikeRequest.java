package com.hongdatchy.parkingspaceandbikeshare.entities.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentBikeRequest {
    int bikeId;
    String paymentMethod;
}
