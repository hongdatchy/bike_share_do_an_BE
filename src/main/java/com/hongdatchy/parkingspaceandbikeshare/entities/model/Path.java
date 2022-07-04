package com.hongdatchy.parkingspaceandbikeshare.entities.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "path")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Path {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "contract_id", nullable = false)
    private Integer contractId;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "routes", nullable = false, columnDefinition = "longtext")
    private String routes;

}
