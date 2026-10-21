package com.omanfootball.manager.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_match_reports")
@Data
public class AIMatchReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;



    @Column(columnDefinition = "TEXT")
    private String summary;



    private String keyPlayer;



    @Column(columnDefinition = "TEXT")
    private String aiAnalysis;



    private Double confidenceScore;



    private LocalDateTime createdAt;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", unique = true)
    @JsonIgnore
    private Match match;



    @PrePersist
    public void createDate() {
        this.createdAt = LocalDateTime.now();
    }

}
