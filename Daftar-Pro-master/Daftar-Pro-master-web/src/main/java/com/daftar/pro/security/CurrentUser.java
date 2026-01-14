package com.daftar.pro.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.daftar.pro.entity.User;
import com.daftar.pro.exception.ResourceNotFoundException;
import com.daftar.pro.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CurrentUser {

	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public User get() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new ResourceNotFoundException("No authenticated user in context");
		}

		String username = authentication.getName();
		return userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new ResourceNotFoundException("User %s not found".formatted(username)));
	}
}

