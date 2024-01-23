package com.eprocess.assetmanager.dtos.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDto {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "position is required")
    private String position;
    @NotBlank(message = "phone is required")
    private String phone;
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "team is required")
    private String team;
    @NotBlank(message = "team lead is required")
    private String teamLead;
    @NotBlank(message = "organization is required")
    private String organization;
}
