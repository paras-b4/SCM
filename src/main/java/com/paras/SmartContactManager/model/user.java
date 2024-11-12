package com.paras.SmartContactManager.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "contacts")
public class user implements UserDetails { // Changed class name to User

    @Id
    private String userId;
    private String name;
    private String email;
    
    @Getter(AccessLevel.NONE) // Prevent direct access to the password
    private String password;
    
    private String about;
    private String profilepic;
    private String phoneNumber;

    private boolean enabled = false;
    private boolean emailVerified = false;
    private boolean phoneVerified = false;

    private Providers provider = Providers.self; // Assuming Providers is an enum
    private String providerUserId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contact> contacts = new ArrayList<>(); // Changed to plural for clarity

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> rolelist = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolelist.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
    public boolean isEnabled() { // Added method to check if the account is enabled
        return enabled;
    }
}
