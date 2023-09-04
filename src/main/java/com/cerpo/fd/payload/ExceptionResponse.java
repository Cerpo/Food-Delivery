package com.cerpo.fd.payload;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@RequiredArgsConstructor
@JsonPropertyOrder({
		"httpStatus",
		"message",
		"issuedAt"
})
public class ExceptionResponse {
	@JsonProperty("httpStatus")
	private final HttpStatus httpStatus;

	@JsonProperty("message")
	private final String message;

	@JsonProperty("issuedAt")
	private final Date issuedAt;
}
