package com.example.Site.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreareContRequest {
    private String username;
    private String email;
    private String password;
}
