package com.book.exchange.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.book.exchange.entity.User;
import com.book.exchange.exception.PasswordNotMatchedException;
import com.book.exchange.exception.UserNotRegisteredException;
import com.book.exchange.model.payload.request.ProfileUpdateRequest;
import com.book.exchange.model.payload.response.UserResponse;
import com.book.exchange.repository.UserRepository;
import com.book.exchange.utility.RandomPasswordGenerator;

@Service
public class ProfileService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String accountRecovery(ProfileUpdateRequest profileUpdateRequest) {
		User user = userRepository.findByUsername(profileUpdateRequest.getUsername())
		        .orElseThrow(() -> new UserNotRegisteredException(" User is not found."));

		if (!user.getEmail().equals(profileUpdateRequest.getEmail())) {
			throw new PasswordNotMatchedException("Details are not matched in the System.");
		}
		String plainPassword = RandomPasswordGenerator.randomPasswordGenerator();
		user.setPassword(passwordEncoder.encode(plainPassword));
		user.setIsactive(1l);
		user.setAttempts(0l);

		userRepository.saveAndFlush(user);
		return "Your new password is %s".formatted(plainPassword);
	}

	public UserResponse update(ProfileUpdateRequest profileUpdateRequest) {
		User user = userRepository.findByUsername(profileUpdateRequest.getUsername())
		        .orElseThrow(() -> new UserNotRegisteredException(" User is not found."));
		updateProfile(profileUpdateRequest, user);
		userRepository.saveAndFlush(user);
		return UserResponse.builder().username(user.getUsername()).email(user.getEmail()).id(user.getId())
		        .phone(user.getPhone())
		        .roles(user.getRoles().stream().map(role -> role.getName().toString()).collect(Collectors.toSet()))
		        .build();
	}

	private void updateProfile(ProfileUpdateRequest profileUpdateRequest, User user) {

		String currentPassword = profileUpdateRequest.getCurrentPassword();
		String newPassword = profileUpdateRequest.getNewPassword();
		String confirmPassword = profileUpdateRequest.getConfirmPassword();

		if (StringUtils.hasText(newPassword) && StringUtils.hasText(confirmPassword) && newPassword.equals(confirmPassword)) {
			if (passwordEncoder.matches(currentPassword, user.getPassword())) {
				user.setPassword(passwordEncoder.encode(newPassword));
			} else {
				throw new PasswordNotMatchedException("Wrong Password !!! ");
			}
		}

		if (StringUtils.hasText(profileUpdateRequest.getEmail())) {
			user.setEmail(profileUpdateRequest.getEmail());
		}

		if (StringUtils.hasText(profileUpdateRequest.getPhone())) {
			user.setPhone(profileUpdateRequest.getPhone());
		}

		if (!ObjectUtils.isEmpty(profileUpdateRequest.getRoles())) {
			user.setRoles(profileUpdateRequest.getRoles());
		}
	}
}
