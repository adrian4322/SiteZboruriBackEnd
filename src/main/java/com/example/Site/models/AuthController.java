package com.example.Site.models;

import com.example.Site.repositories.UtilizatorRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, String>> logareUtilizator(@RequestBody LogareRequest logareRequest, HttpSession session) {
        Utilizator utilizator = utilizatorRepository.findByUsername(logareRequest.getUsername());
        Map<String, String> raspuns = new HashMap<>();

        System.out.println(logareRequest.getUsername() + " / " +  logareRequest.getPassword());

        if (utilizator == null || !passwordEncoder.matches(logareRequest.getPassword(), utilizator.getParola())) {
            raspuns.put("eroare", "Nume de utilizator sau parola gresite!");
            return ResponseEntity.status(401).body(raspuns);
        }

        session.setAttribute("utilizator", utilizator);

        raspuns.put("Succes", " Logarea a fost cu succes!");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(raspuns);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {

        session.invalidate();
        return ResponseEntity.ok("Logout reusit!");
    }

    @PostMapping("/creareCont")
    public ResponseEntity<Map<String, String>> inregistrareUtilizator(@RequestBody CreareContRequest creareContRequest) {
        Map<String, String> response = new HashMap<>();

        if (utilizatorRepository.existsByUsername(creareContRequest.getUsername())) {
            response.put("eroare:", " Numele de utilizator este luat!");
            return ResponseEntity
                    .badRequest()
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(response);
        }

        if (utilizatorRepository.existsByEmail(creareContRequest.getEmail())) {
            response.put("eroare", " Email-ul este luat!");
            return ResponseEntity
                    .badRequest()
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(response);
        }

        Utilizator utilizator = new Utilizator(
                creareContRequest.getUsername(),
                creareContRequest.getEmail(),
                creareContRequest.getPassword()
        );

        utilizatorService.saveUtilizator(utilizator);

        response.put("Succes", "Utilizator creat!");
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(response);
    }
}

