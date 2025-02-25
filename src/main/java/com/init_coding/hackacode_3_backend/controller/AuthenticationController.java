package com.init_coding.hackacode_3_backend.controller;

import com.init_coding.hackacode_3_backend.dto.request.AuthCreateUserRequest;
import com.init_coding.hackacode_3_backend.dto.request.AuthLoginRequest;
import com.init_coding.hackacode_3_backend.dto.response.AuthResponse;
import com.init_coding.hackacode_3_backend.exception.InvalidArgumentException;
import com.init_coding.hackacode_3_backend.service.impl.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> registrarse(@RequestBody @Valid AuthCreateUserRequest authCreateUserRequest) throws InvalidArgumentException {

        return ResponseEntity.ok(userDetailsService.createUser(authCreateUserRequest));
    }
    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> iniciarSesion(@RequestBody @Valid AuthLoginRequest authLoginRequest){

        return ResponseEntity.ok(userDetailsService.loginUser(authLoginRequest));
    }

    @GetMapping("/verificar-token")
    public ResponseEntity<?> validar(){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
