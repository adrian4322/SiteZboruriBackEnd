package com.example.Site.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@Table(name = "utilizatori")
public class Utilizator {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String password;

    public Utilizator(long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Utilizator() {}



}
