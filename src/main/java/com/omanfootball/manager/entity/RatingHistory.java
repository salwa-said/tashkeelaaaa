package com.omanfootball.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name = "rating_history")
@Data
public class RatingHistory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;



    private Integer oldRating;



    private Integer newRating;



    private String reason;



    private LocalDateTime changeDate;



    private LocalDateTime createdAt;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private Player player;



    @PrePersist
    public void createDate() {

        LocalDateTime now = LocalDateTime.now();


        if (this.changeDate == null) {
            this.changeDate = now;
        }


        this.createdAt = now;

    }

}