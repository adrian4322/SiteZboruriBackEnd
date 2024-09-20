package com.example.Site.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogareRequest {
    private String username;
    private String password;
}
