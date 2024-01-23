package com.eprocess.assetmanager.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserDto {
    private Long id;
    private String username;
    private String email;
    private String position;
    private String phone;
    private String team;
    private String teamLead;
    private String organization;
    private Date createdAt;
    private Date updatedAt;
}
