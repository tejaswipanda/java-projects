package com.test.application.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.test.application.controller.ClientController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ClientService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private RestTemplate restTemplate;

	@Async
	@CircuitBreaker(name = "clientService", fallbackMethod = "handleErrorAsync")
	public CompletableFuture<ResponseEntity<?>> consumeServerURLAsync() {
		CompletableFuture.runAsync(() -> {

			for (int i = 0; i < 5000; i++) {
				LOGGER.info("Consuming URL : {}", i);
				try {
					restTemplate.getForEntity("http://localhost:8081/circuitBreaker/test", String.class);
				} catch (Exception e) {
					throw new RuntimeException("errorrr");
				}
			}
		});

		return CompletableFuture.completedFuture(new ResponseEntity<String>("Success!!!", HttpStatus.OK));

	}

	@CircuitBreaker(name = "clientService", fallbackMethod = "handleError")
	public ResponseEntity<?> consumeServerURL() {
		for (int i = 0; i < 5000; i++) {
			LOGGER.info("Consuming URL : {}", i);
			restTemplate.getForEntity("http://localhost:8081/circuitBreaker/test", String.class);
		}
		return ResponseEntity.ok("Success !!!");
	}

	public CompletableFuture<ResponseEntity<?>> handleErrorAsync(Throwable throwable) {
		return CompletableFuture
				.completedFuture(new ResponseEntity<String>("Fallback Method is Active", HttpStatus.OK));
	}

	public ResponseEntity<?> handleError(Throwable throwable) {
		return new ResponseEntity<String>("Fallback Method is Active", HttpStatus.OK);
	}
}
