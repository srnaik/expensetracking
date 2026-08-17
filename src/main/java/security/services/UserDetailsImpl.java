package security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class UserDetailsImpl implements UserDetails {

    private UUID id;

    private String username;
    private String firstName;
    private String lastName;

    @JsonIgnore
    private String passwordHash;


    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(UUID id, String username, String password, String firstName,
        String lastName) {
        this.id = id;
        this.username = username;
        this.passwordHash = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(user.getId(),
                user.getUsername(),
                user.getPasswordHash(),
                user.getFirstName(),
                user.getLastName());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();    }

    @Override
    public @Nullable String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public UUID getId(){
        return id;
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


