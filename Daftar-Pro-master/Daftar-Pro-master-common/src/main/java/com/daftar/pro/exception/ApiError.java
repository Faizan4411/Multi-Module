package com.daftar.pro.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApiError {
	Instant timestamp;
	int status;
	String error;
	String message;
	String path;

	public static ApiError of(HttpStatus status, String message, String path) {
		return ApiError.builder()
				.timestamp(Instant.now())
				.status(status.value())
				.error(status.getReasonPhrase())
				.message(message)
				.path(path)
				.build();
	}
}

