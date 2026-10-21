package com.omanfootball.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "fantasy_challenges")
@Data
public class FantasyChallenge {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long challengeId;



    private String title;



    @Column(columnDefinition = "TEXT")
    private String description;



    private String challengeType;



    private Integer rewardPoints;



    private LocalDate startDate;



    private LocalDate endDate;



    private LocalDateTime createdAt;



    @OneToMany(
            mappedBy = "challenge",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<UserChallenge> usersChallenges;



    @PrePersist
    public void createDate() {
        this.createdAt = LocalDateTime.now();
    }

}