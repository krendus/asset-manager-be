package com.eprocess.assetmanager.auth;

import com.eprocess.assetmanager.configs.JwtService;
import com.eprocess.assetmanager.dtos.user.*;
import com.eprocess.assetmanager.enums.Role;
import com.eprocess.assetmanager.exceptions.EmailTakenException;
import com.eprocess.assetmanager.exceptions.NotAuthenticatedException;
import com.eprocess.assetmanager.exceptions.UsernameTakenException;
import com.eprocess.assetmanager.models.User;
import com.eprocess.assetmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    public RegisterResponseDto register(RegisterRequestDto request) throws RuntimeException {
        Optional<User> dbUser = repository.findByUsername(request.getUsername());
        if(dbUser.isPresent()) {
            throw new UsernameTakenException("username taken");
        }

        dbUser = repository.findByEmail(request.getEmail());
        if(dbUser.isPresent()) {
            throw new EmailTakenException("email taken");
        }
        var user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .organization(request.getOrganization())
                .phone(request.getPhone())
                .position(request.getPosition())
                .team(request.getTeam())
                .teamLead(request.getTeamLead())
                .createdAt(new Date())
                .updatedAt(new Date())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        User savedUser = repository.save(user);
        return RegisterResponseDto.builder()
                .id(savedUser.getId())
                .build();
    }
    public LoginResponseDto login(LoginRequestDto request) throws RuntimeException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch(AuthenticationException e) {
            throw new NotAuthenticatedException("invalid username or password");
        }
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var userDto = modelMapper.map(user, GetUserDto.class);
        var jwtToken = jwtService.generateToken(user);
        return LoginResponseDto.builder()
                .user(userDto)
                .token(jwtToken)
                .build();
    }
}