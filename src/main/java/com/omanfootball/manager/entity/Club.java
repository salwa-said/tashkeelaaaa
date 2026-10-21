package com.omanfootball.manager.entity;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "clubs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Club {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @NotBlank(message = "Club name is required")
    @Size(max = 100, message = "Club name must be at most 100 characters")
    @Pattern(regexp = "[\\p{L}\\p{M} .’'&-]+", message = "Club name may contain letters, spaces, apostrophes, ampersands, periods, and hyphens only")
    @Column(nullable = false)
    private String clubName;



    @Size(max = 20, message = "Short name must be at most 20 characters")
    private String shortName;



    @Size(max = 100, message = "City must be at most 100 characters")
    private String city;



    private String logo;



    private String description;



    @Min(value = 0, message = "Trophies cannot be negative")
    private int trophies;


}
