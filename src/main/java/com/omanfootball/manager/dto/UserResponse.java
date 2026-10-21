package com.omanfootball.manager.dto;


import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class UserResponse {



    private Long userId;



    private String fullName;



    private String email;



    private String role;



}