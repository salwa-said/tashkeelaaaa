package com.omanfootball.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name = "club_dashboards")
@Data
public class ClubDashboard {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dashboardId;



    private Double squadStrength;



    private String teamForm;



    private String topPlayer;



    @Column(columnDefinition = "TEXT")
    private String weaknesses;



    private Double averageRating;



    private Integer totalPlayers;



    private LocalDateTime lastUpdated;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", unique = true)
    @JsonIgnore
    private Club club;



    @PrePersist
    @PreUpdate
    public void updateLastUpdated() {
        this.lastUpdated = LocalDateTime.now();
    }

}