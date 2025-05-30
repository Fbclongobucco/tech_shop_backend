package com.buccodev.tech_shop.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users_system_tb")
public class UserSystem implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 300)
    private String password;

    @Column(unique = true, nullable = false, length = 150)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;

    public UserSystem() {}

    public UserSystem(Long id, String name, String password, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = Roles.BASIC;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return email;
    }

    public void setUsername(String name) {
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == Roles.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_" + Roles.BASIC.getRole().toUpperCase()),
                           new SimpleGrantedAuthority("ROLE_" + Roles.ADMIN.getRole().toUpperCase()));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name().toUpperCase()));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role.getRole();
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
