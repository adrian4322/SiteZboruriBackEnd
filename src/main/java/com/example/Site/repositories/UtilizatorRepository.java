package com.example.Site.repositories;

import com.example.Site.models.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilizatorRepository extends JpaRepository<Utilizator, Integer> {


    Utilizator findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);


}
