package com.example.Site.models;

import com.example.Site.repositories.UtilizatorRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UtilizatorService utilizatorService;
    private final UtilizatorRepository utilizatorRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpSession session;

    public AuthController(UtilizatorService utilizatorService,
                          UtilizatorRepository utilizatorRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          HttpSession session) {
        this.utilizatorService = utilizatorService;
        this.utilizatorRepository = utilizatorRepository;
        this.passwordEncoder = passwordEncoder;
        this.session = session;
    }

    @PostMapping("/login")
    public ResponseEntity<String> logareUtilizator(@RequestBody LogareRequest logareRequest, HttpSession session) {
        Utilizator utilizator = utilizatorRepository.findByUsername(logareRequest.getUsername());

        if (utilizator == null || !passwordEncoder.matches(logareRequest.getPassword(), utilizator.getParola())) {
            return ResponseEntity.status(401).body("eroare: Nume de utilizator sau parola gresite!");
        }

        session.setAttribute("utilizator", utilizator);

        return ResponseEntity.ok("Logare reusita!");
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {

        session.invalidate();
        return ResponseEntity.ok("Logout reusit!");
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