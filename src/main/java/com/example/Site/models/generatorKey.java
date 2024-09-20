package com.example.Site.models;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import  java.security.SecureRandom;
import  java.util.Base64;

@Configuration
public class generatorKey {
    @Bean(name = "cheie")
    public String generareKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[32];
        secureRandom.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

}
