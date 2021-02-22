package ru.oleg.sketchbook.model.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ru.oleg.sketchbook.model.StatusAccount;
import ru.oleg.sketchbook.model.StatusEmail;
import ru.oleg.sketchbook.model.Client;

import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {
    private final StatusEmail statusEmail;
    private final StatusAccount statusAccount;
    private final String email;
    private final String password;
    private final List<SimpleGrantedAuthority> simpleGrantedAuthorities;

    public SecurityUser(StatusEmail statusEmail, StatusAccount statusAccount, String email, String password, List<SimpleGrantedAuthority> simpleGrantedAuthorities) {
        this.statusEmail = statusEmail;
        this.statusAccount = statusAccount;
        this.email = email;
        this.password = password;
        this.simpleGrantedAuthorities = simpleGrantedAuthorities;
    }

    public static UserDetails convert(Client client){
        return new User(
                client.getEmail(),
                client.getPassword(),
                client.getStatusAccount().equals(StatusAccount.ACTIVE),
                client.getStatusAccount().equals(StatusAccount.ACTIVE),
                client.getStatusAccount().equals(StatusAccount.ACTIVE),
                client.getStatusEmail().equals(StatusEmail.YES),
                client.getRole().getAuthorities()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return StatusEmail.YES.equals(statusEmail);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return StatusAccount.ACTIVE.equals(statusAccount);
    }
}
