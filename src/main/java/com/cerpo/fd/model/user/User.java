package com.cerpo.fd.model.user;

import java.util.Date;
import java.util.List;
import java.util.Collection;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @Column(name = "email")
    private String email;

    @NotBlank
    @Column(name = "password")
    private String password;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_login_date")
    private Date lastLoginDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public User(String email, String password, Date currentDate, Role role) {
        this.email         = email;
        this.password      = password;
        this.createdDate   = currentDate;
        this.lastLoginDate = currentDate;
        this.role          = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
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
