package com.daftar.pro.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.daftar.pro.entity.User;
import com.daftar.pro.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameIgnoreCase(username)
				.or(() -> userRepository.findByEmailIgnoreCase(username))
				.orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(username)));

		return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
				.password(user.getPassword())
				.accountLocked(!Boolean.TRUE.equals(user.getAccountNonLocked()))
				.disabled(!Boolean.TRUE.equals(user.getEnabled()))
				.authorities(extractAuthorities(user))
				.build();
	}

	private Collection<? extends GrantedAuthority> extractAuthorities(User user) {
		Set<GrantedAuthority> authorities = new HashSet<>();

		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode().name()));
			role.getPrivileges()
					.forEach(privilege -> authorities.add(new SimpleGrantedAuthority(privilege.getCode())));
		});

		return authorities;
	}
}

