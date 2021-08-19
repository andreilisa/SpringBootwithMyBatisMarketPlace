package com.example.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String email;
    private UserRole userRole;
    private boolean locked ;
    private boolean enabled = true;
    private List<Products> products;

        public User(String username,
                String password,
                String email,
                UserRole user) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = user;

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(UserRole.USER.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return username;
    }


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

    public boolean isEnabled() {
        return enabled;
    }
}
