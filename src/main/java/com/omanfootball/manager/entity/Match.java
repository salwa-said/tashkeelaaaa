package com.omanfootball.manager.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @NotNull(message = "Home club is required")
    @ManyToOne
    @JoinColumn(name = "home_club_id")
    private Club homeClub;




    @NotNull(message = "Away club is required")
    @ManyToOne
    @JoinColumn(name = "away_club_id")
    private Club awayClub;





    @NotNull(message = "Match date is required")
    private LocalDateTime matchDate;





    @NotBlank(message = "Venue is required")
    @Size(max = 150, message = "Venue must be at most 150 characters")
    private String venue;





    @Min(value = 0, message = "Home score cannot be negative")
    private int homeScore;





    @Min(value = 0, message = "Away score cannot be negative")
    private int awayScore;





    @Size(max = 30, message = "Status must be at most 30 characters")
    private String status;


}
