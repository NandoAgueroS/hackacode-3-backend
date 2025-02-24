package com.init_coding.hackacode_3_backend.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthCreateUserRequest(@NotBlank String username,
                                    @NotBlank String password,
                                    AuthCreateRoleRequest roleRequest) {
}
