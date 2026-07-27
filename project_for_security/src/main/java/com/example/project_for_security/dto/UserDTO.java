package com.example.project_for_security.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


@Data
public class UserDTO {
    private String username;
    private String password;

}
