package com.omanfootball.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name = "player_comparisons")
@Data
public class PlayerComparison {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comparisonId;



    private Integer goalsDifference;



    private Integer assistsDifference;



    private Double ratingDifference;



    private Double fantasyPointsDifference;



    @Column(columnDefinition = "TEXT")
    private String aiExplanation;



    private LocalDateTime createdAt;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_one_id")
    @JsonIgnore
    private Player playerOne;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_two_id")
    @JsonIgnore
    private Player playerTwo;



    @PrePersist
    public void createDate() {
        this.createdAt = LocalDateTime.now();
    }

}