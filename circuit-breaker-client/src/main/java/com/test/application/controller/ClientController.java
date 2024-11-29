package com.test.application.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.application.service.ClientService;

@RestController
@RequestMapping("circuitBreaker")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@GetMapping("testCiruitBreakerAsync")
	public CompletableFuture<ResponseEntity<?>> testCiruiteBreakerAsync() {
		return clientService.consumeServerURLAsync();
	}

	@GetMapping("testCiruitBreaker")
	public ResponseEntity<?> testCiruiteBreaker() {
		return clientService.consumeServerURL();
	}

}
