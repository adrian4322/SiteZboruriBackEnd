package com.example.Site.models;

import com.example.Site.repositories.UtilizatorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UtilizatorService utilizatorService;
    private final UtilizatorRepository utilizatorRepository;

    public AuthController(UtilizatorService utilizatorService, UtilizatorRepository utilizatorRepository) {
        this.utilizatorService = utilizatorService;
        this.utilizatorRepository = utilizatorRepository;
    }

    @PostMapping("/creareCont")
    public ResponseEntity<String> inregistrareUtilizator(@RequestBody CreareContRequest creareContRequest) {
        if(utilizatorRepository.existsByUsername(creareContRequest.getUsername()))
            return ResponseEntity
                    .badRequest()
                    .body("eroare: Numele de utilizator este luat!");

        if(utilizatorRepository.existsByEmail(creareContRequest.getEmail()))
            return ResponseEntity
                    .badRequest()
                    .body("eroare: Email-ul este luat!");

        Utilizator utilizator = new  Utilizator(creareContRequest.getUsername(),
                creareContRequest.getEmail(),
                creareContRequest.getPassword());

        utilizatorService.saveUtilizator(utilizator);

        return ResponseEntity.ok("Utilizator creat!");
    }


}