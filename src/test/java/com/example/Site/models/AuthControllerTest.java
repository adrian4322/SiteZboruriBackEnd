package com.example.Site.models;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import com.example.Site.models.generatorKey;
import com.example.Site.repositories.UtilizatorRepository;

import org.aspectj.lang.annotation.Before;
import org.aspectj.weaver.BCException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import  java.security.SecureRandom;
import  java.util.Base64;
import java.util.Map;


public class AuthControllerTest {

	@Mock
	private UtilizatorRepository utilizatorRepository;

	@Mock
	private UtilizatorService utilizatorService;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@InjectMocks
	private AuthController authController;

	@BeforeEach()
	void setUp() {
		MockitoAnnotations.openMocks(this);  // Cu metoda asta initializam mock-urile si Mockito injecteaza clasele cu @InjecteMocks
	}
	
	@Test
	public void inregistrareUtilizatorTest_usernameExists() {
		CreareContRequest creareContRequest = new CreareContRequest("test", "test@gmail.com", "testParola");

		when(utilizatorRepository.existsByUsername("test")).thenReturn(true);

		ResponseEntity<Map<String, String>> response = authController.inregistrareUtilizator(creareContRequest);

        assertEquals(" Numele de utilizator este luat!", response.getBody().get("eroare:"));
		assertEquals(BAD_REQUEST, response.getStatusCode());
	}

	@Test 
	public void inregistrareUtilizatorTest_emailExists() {
		CreareContRequest creareContRequest = new CreareContRequest("test", "test@gmail.com", "testParola");

		when(utilizatorRepository.existsByEmail("test@gmail.com")).thenReturn(true);

		ResponseEntity<Map<String, String>> response = authController.inregistrareUtilizator(creareContRequest);

		assertEquals(BAD_REQUEST, response.getStatusCode());
		assertEquals("Email-ul este luat!", response.getBody().get("eroare"));

	}

	@Test
	public void inregistrareUtilizator_success() {
		CreareContRequest creareContRequest = new CreareContRequest("test", "test@gmail.com", "testParola");

		when(utilizatorRepository.existsByEmail("test@gmail.com")).thenReturn(false);
		when(utilizatorRepository.existsByUsername("test")).thenReturn(false);

		ResponseEntity<Map<String, String>> response = authController.inregistrareUtilizator(creareContRequest);

		verify(utilizatorService, times(1)).saveUtilizator(any(Utilizator.class));

		assertEquals("Utilizator creat!", response.getBody().get("Succes"));
		assertEquals(OK, response.getStatusCode());
	}

	@Test
	public void logareUtilizator_UtilizatorNotFound(){
		LogareRequest logare = new LogareRequest("test", "parolaTest");

		when(utilizatorRepository.findByUsername("test")).thenReturn(null);

		ResponseEntity<Map<String, String>> response = authController.logareUtilizator(logare, null);

		assertEquals(UNAUTHORIZED, response.getStatusCode()); // code 401
		assertEquals("Nume de utilizator sau parola gresite!", response.getBody().get("eroare"));
	}

	@Test
	public void logareUtilizator_parolaTesting() {
		LogareRequest logare = new LogareRequest("test", "parolaTest");
		Utilizator utilizator = new Utilizator();
		utilizator.setParola("parolaGresita");

		when(utilizatorRepository.findByUsername("testUser")).thenReturn(utilizator);
		when(passwordEncoder.matches(logare.getPassword(), utilizator.getParola())).thenReturn(false);

		ResponseEntity<Map<String, String>> response = authController.logareUtilizator(logare, null);

		assertEquals(UNAUTHORIZED, response.getStatusCode());
		assertEquals("Nume de utilizator sau parola gresite!", response.getBody().get("eroare"));

	}
}
