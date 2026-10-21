package com.omanfootball.manager.dto;


import lombok.Data;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



@Data
public class PlayerRequest {



    @NotBlank(message = "Player name is required")
    @Size(max = 100, message = "Player name must be at most 100 characters")
    @Pattern(regexp = "[\\p{L}\\p{M} .’'-]+", message = "Player name contains unsupported characters")
    private String name;



    @NotBlank(message = "Player position is required")
    @Pattern(regexp = "(?i)GOALKEEPER|DEFENDER|MIDFIELDER|FORWARD|GK|DF|MF|FW", message = "Choose a valid player position")
    private String position;



    @NotNull(message = "Player age is required")
    @Min(value = 15, message = "Player age must be at least 15")
    @Max(value = 60, message = "Player age cannot exceed 60")
    private Integer age;



    @NotNull(message = "Player price is required")
    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    private Double price;



    @NotNull(message = "Overall rating is required")
    @DecimalMin(value = "0.0", message = "Overall rating must be between 0 and 100")
    @jakarta.validation.constraints.DecimalMax(value = "100.0", message = "Overall rating must be between 0 and 100")
    private Double overallRating;



    @NotNull(message = "Choose a club")
    private Long clubId;

    @Size(max = 80, message = "Nationality must be at most 80 characters")
    private String nationality;

    private String image;



}
