package com.daftar.pro.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.security.jwt")
public class JwtProperties {

	/**
	 * Secret should be at least 32 chars to satisfy the HS256 key length.
	 */
	private String secret = "change-me-now-change-me-now-change-me";

	/**
	 * Token expiration in milliseconds.
	 */
	private long expiration = 3_600_000; // 1 hour
}

