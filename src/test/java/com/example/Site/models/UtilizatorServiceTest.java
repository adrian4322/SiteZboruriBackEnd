package com.example.Site.models;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Site.models.Utilizator;
import com.example.Site.repositories.UtilizatorRepository;
import com.example.Site.models.UtilizatorService;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class UtilizatorServiceTest {

	@Mock
	private UtilizatorRepository utilizatorRepository; //simuleaza UtilizatorRepository

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@InjectMocks
	private UtilizatorService utilizatorService; // injecteaza repository in service

	@BeforeEach()
	void setUp() {
		MockitoAnnotations.openMocks(this);  // Cu metoda asta initializam mock-urile si Mockito injecteaza clasele cu @InjecteMocks
	}
	
	@Test
	public void testSaveUtilizator() {
		Utilizator utilizator = new Utilizator();
		utilizator.setParola("parolaTest");

		when(passwordEncoder.encode("parolaTest")).thenReturn("parolaCriptata");

		utilizatorService.saveUtilizator(utilizator);

		verify(passwordEncoder, times(1)).encode("parolaTest");

		verify(utilizatorRepository, times(1)).save(any(Utilizator.class));

       assert(utilizator.getParola().equals("parolaCriptata"));
	}
}
