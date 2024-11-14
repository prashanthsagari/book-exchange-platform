package com.book.exchange.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.book.exchange.entity.User;
import com.book.exchange.exception.UserInActiveException;
import com.book.exchange.exception.UserNotRegisteredException;
import com.book.exchange.model.payload.request.SignInRequest;
import com.book.exchange.model.payload.response.TokenResponse;
import com.book.exchange.model.payload.response.UserResponse;
import com.book.exchange.repository.UserRepository;
import com.book.exchange.security.UserReturnDataContextHolder;
import com.book.exchange.utility.JWTUtility;

@Service
public class SignInService {

	@Autowired
	private JWTUtility jwtUtility;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public TokenResponse signIn(final SignInRequest singInRequest) {

		UserReturnDataContextHolder.setLoginRequest(singInRequest);
		UserResponse userResponse = null;
		String jwt = null;
		User user = userRepository.findByUsername(singInRequest.getUsername())
		        .orElseThrow(() -> new UserNotRegisteredException(" User is not registered in the system."));
		if (user.getIsactive() != 1) {
			throw new UserInActiveException("User is deactivated.");
		}
		boolean isValid = passwordEncoder.matches(singInRequest.getPassword(), user.getPassword());
		if (isValid) {
			userResponse = UserResponse.builder().id(user.getId()).attempts(user.getAttempts())
			        .username(user.getUsername()).email(user.getEmail()).phone(user.getPhone())
			        .isactive(user.getIsactive())
			        .roles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()))
			        .build();
			jwt = jwtUtility.generateJwtToken(userResponse);
			Long attempts = user.getAttempts();

			if (attempts >= 1) {
				user.setAttempts(0L);
				userRepository.saveAndFlush(user);
			}

		} else {
			throw new BadCredentialsException("Wrong Credentials");
		}

		return new TokenResponse(jwt, userResponse);
	}
}
