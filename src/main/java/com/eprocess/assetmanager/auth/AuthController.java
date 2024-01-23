package com.eprocess.assetmanager.auth;

import com.eprocess.assetmanager.dtos.ResponseDto;
import com.eprocess.assetmanager.dtos.user.LoginRequestDto;
import com.eprocess.assetmanager.dtos.user.LoginResponseDto;
import com.eprocess.assetmanager.dtos.user.RegisterRequestDto;
import com.eprocess.assetmanager.dtos.user.RegisterResponseDto;
import com.eprocess.assetmanager.exceptions.EmailTakenException;
import com.eprocess.assetmanager.exceptions.NotAuthenticatedException;
import com.eprocess.assetmanager.exceptions.UsernameTakenException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(
           @Valid @RequestBody RegisterRequestDto request
    ) {
        try {
            RegisterResponseDto result = service.register(request);
            ResponseDto response = ResponseDto.builder()
                    .status("success")
                    .message("signup successfully")
                    .data(result)
                    .build();
            return ResponseEntity.status(201).body(response);
        } catch (UsernameTakenException e) {
            ResponseDto response = ResponseDto.builder()
                    .status("error")
                    .message("username taken")
                    .build();
            return ResponseEntity.status(400).body(response);
        } catch (EmailTakenException e){
            ResponseDto response = ResponseDto.builder()
                    .status("error")
                    .message("email taken")
                    .build();
            return ResponseEntity.status(400).body(response);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(
           @Valid @RequestBody LoginRequestDto request
    ) {
        try {
            LoginResponseDto result = service.login(request);
            ResponseDto response = ResponseDto.builder()
                    .status("success")
                    .message("login successfully")
                    .data(result)
                    .build();
            return ResponseEntity.ok(response);
        } catch (NotAuthenticatedException e) {
            ResponseDto response = ResponseDto.builder()
                    .status("error")
                    .message("invalid username or password")
                    .build();
            return ResponseEntity.status(401).body(response);
        }
    }
}
