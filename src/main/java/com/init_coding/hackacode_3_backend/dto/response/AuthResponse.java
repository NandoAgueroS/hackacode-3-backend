package com.init_coding.hackacode_3_backend.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message", "jwt", "status"})
public record AuthResponse(String username,
                           String message,
                           String jwt,
                           boolean status) {
}
