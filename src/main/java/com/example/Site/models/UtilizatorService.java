package com.example.Site.models;

import com.example.Site.repositories.UtilizatorRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilizatorService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UtilizatorRepository utilizatorRepository;

    public UtilizatorService(BCryptPasswordEncoder passwordEncoder, UtilizatorRepository utilizatorRepository){
        this.passwordEncoder = passwordEncoder;
        this.utilizatorRepository = utilizatorRepository;
    }

    public Utilizator saveUtilizator(Utilizator utilizator) {
        String hashedPassword = passwordEncoder.encode(utilizator.getPassword());
        utilizator.setPassword(hashedPassword);

        return utilizatorRepository.save(utilizator);
    }


}
