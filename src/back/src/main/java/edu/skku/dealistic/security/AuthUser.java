package edu.skku.dealistic.security;

import edu.skku.dealistic.model.Authority;
import edu.skku.dealistic.model.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
public class AuthUser implements UserDetails {

    private String id;
    private String name;
    private String password;
    private String organization;
    private Authority authority;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
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

    public static AuthUser from(User user) {
        return builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .organization(user.getOrganization())
                .authority(user.getAuthority())
                .build();
    }
}
