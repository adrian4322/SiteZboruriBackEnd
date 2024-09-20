package com.example.Site.models;

import com.example.Site.repositories.UtilizatorRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UtilizatorService utilizatorService;
    private final UtilizatorRepository utilizatorRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpSession session;
    private final String secretKey;

    public AuthController(UtilizatorService utilizatorService,
                          UtilizatorRepository utilizatorRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          HttpSession session,
                          @Qualifier("cheie") String secretKey) {
        this.utilizatorService = utilizatorService;
        this.utilizatorRepository = utilizatorRepository;
        this.passwordEncoder = passwordEncoder;
        this.session = session;
        this.secretKey = secretKey;
    }

    @PostMapping("/logare")
    public ResponseEntity<Map<String, String>> logareUtilizator(@RequestBody LogareRequest logareRequest, HttpSession session) {
        Utilizator utilizator = utilizatorRepository.findByUsername(logareRequest.getUsername());
        Map<String, String> raspuns = new HashMap<>();

        if (utilizator == null || !passwordEncoder.matches(logareRequest.getPassword(), utilizator.getParola())) {
            raspuns.put("eroare", "Nume de utilizator sau parola gresite!");
            return ResponseEntity.status(401).body(raspuns);
        }

        String token = Jwts.builder()
                .setSubject(utilizator.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        session.setAttribute("utilizator", utilizator);

        raspuns.put("Succes", " Logarea a fost cu succes!");
        raspuns.put("token", token);
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
            response.put("eroare", "Email-ul este luat!");
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

