package com.hongdatchy.parkingspaceandbikeshare.repository;

import com.hongdatchy.parkingspaceandbikeshare.entities.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, Integer> {
    //        return stationRepository.findAll().stream().map(station -> {
//            return StationResponse.builder()
//                    .id(station.getId())
//                    .distanceToThisStation(Common.calculateDistance(myLat, myLong,station.getLatitude(), station.getLongitude()))
//                    .currentNumberCar(station.getCurrentNumberCar())
//                    .location(station.getLocation())
//                    .name(station.getName())
//                    .latitude(station.getLatitude())
//                    .longitude(station.getLongitude())
//                    .build();
//        }).sorted(new Comparator<StationResponse>() {
//                    @Override
//                    public int compare(StationResponse o1, StationResponse o2) {
//                        return Double.compare(o1.getDistanceToThisStation(), o2.getDistanceToThisStation());
//                    }
//        }).collect(Collectors.toList());

}