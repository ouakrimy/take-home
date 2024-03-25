package com.bell.takehome.model;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, HttpStatus status, long timestamp) {
}
