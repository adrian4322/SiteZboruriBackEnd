package com.example.Site.repositories;

import com.example.Site.models.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilizatorRepository extends JpaRepository<Utilizator, Integer> {
    Boolean existsByUsername(String username);  // Changed from existaUsername
    Boolean existsByEmail(String email);        // Changed from existaEmail
}
