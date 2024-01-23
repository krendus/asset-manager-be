package com.eprocess.assetmanager.models;

import com.eprocess.assetmanager.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Column(
        name = "id",
        updatable = false
    )
    private Long id;
    @Column(
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String username;
@Column(
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String email;
    @Column(
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String position;
    @Column(
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String phone;
    @Column(
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String password;
    @Column(
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String team;
    @Column(
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String teamLead;
    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String organization;
    private Date createdAt;
    private Date updatedAt;
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Asset> assets;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
