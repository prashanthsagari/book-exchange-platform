package com.book.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.exchange.model.payload.request.ProfileUpdateRequest;
import com.book.exchange.model.payload.request.SignupRequest;
import com.book.exchange.service.ProfileService;
import com.book.exchange.service.SignUpService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
@Tag(name = "SignUpController", description = "New user registration controller")
public class SignUpController {

	@Autowired
	private SignUpService signUpService;

	@Autowired
	private ProfileService profileService;

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return ResponseEntity.ok(signUpService.registerUser(signUpRequest));
	}

	@PutMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody @Validated ProfileUpdateRequest profileUpdateRequest) {
		return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN)
		        .body(profileService.resetPassword(profileUpdateRequest));
	}
}
