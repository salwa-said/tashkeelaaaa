package com.omanfootball.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name = "what_if_simulations")
@Data
public class WhatIfSimulation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long simulationId;



    @Column(columnDefinition = "TEXT")
    private String originalTeam;



    @Column(columnDefinition = "TEXT")
    private String changes;



    private String formationChange;



    private String captainChange;



    private Double predictedPoints;



    private Double expectedDifference;



    private LocalDateTime createdAt;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;



    @PrePersist
    public void createDate() {
        this.createdAt = LocalDateTime.now();
    }

}