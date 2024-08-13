package com.example.Site.repositories;

import com.example.Site.models.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilizatorRepository extends JpaRepository<Utilizator, Integer> {
<<<<<<< HEAD

    Utilizator findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
=======
    Boolean existsByUsername(String username);  
    Boolean existsByEmail(String email);       
>>>>>>> ce91ea4586f4f9c3e3c061ba01e135a93284f5c0
}
