package com.omanfootball.manager.entity;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



@Entity
@Table(name = "players")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @NotBlank(message = "Player name is required")
    @Size(max = 100, message = "Player name must be at most 100 characters")
    @Pattern(regexp = "[\\p{L}\\p{M} .’'-]+", message = "Player name may contain letters, spaces, apostrophes, periods, and hyphens only")
    @Column(nullable = false)
    private String name;




    @Pattern(regexp = "(?i)GOALKEEPER|DEFENDER|MIDFIELDER|FORWARD|GK|DF|MF|FW", message = "Position must be Goalkeeper, Defender, Midfielder, Forward, GK, DF, MF, or FW")
    private String position;




    @Min(value = 15, message = "Player age must be at least 15")
    @Max(value = 60, message = "Player age cannot exceed 60")
    private int age;




    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    private double price;




    @Min(value = 0, message = "Overall rating must be between 0 and 100")
    @Max(value = 100, message = "Overall rating must be between 0 and 100")
    private double overallRating;




    private int fantasyPoints;




    @Size(max = 80, message = "Nationality must be at most 80 characters")
    private String nationality;




    private String image;




    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;



}
