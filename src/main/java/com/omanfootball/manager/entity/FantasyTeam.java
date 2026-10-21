package com.omanfootball.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "fantasy_team")
@Data
public class FantasyTeam {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;



    @Column(nullable = false)
    private String teamName;



    private Double budget;



    private Integer totalPoints = 0;



    private Double overallRating = 0.0;

    private String formation = "4-4-2";



    private LocalDateTime createdAt;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    @JsonIgnore
    private User user;



    @OneToMany(
            mappedBy = "fantasyTeam",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<FantasyTeamPlayer> players;



    @PrePersist
    public void createDate() {

        this.createdAt = LocalDateTime.now();


        if (this.totalPoints == null) {
            this.totalPoints = 0;
        }


        if (this.overallRating == null) {
            this.overallRating = 0.0;
        }

    }

}
