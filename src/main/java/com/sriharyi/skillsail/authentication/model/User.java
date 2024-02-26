package com.sriharyi.skillsail.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "users")
public class User implements UserDetails{
    @Id
    private String id;
    private String firstname;
    private String lastname;
    @Indexed(unique = true)
    private String email;
    private String password;
    private Set<Role> roles;
    private Set<Permission> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        for(Permission permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission.getName()));
        }
        return authorities;
    }
    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public String getPassword() {
       return this.password;
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
