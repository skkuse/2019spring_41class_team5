package edu.skku.dealistic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @Column(length = 20)
    private String id;

    @Column(length = 100, nullable = false)
    private String name;

    @JsonIgnore
    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String organization;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    @Lob
    @Column
    private Byte[] profileImage;

    @JsonIgnore
    @OneToMany(
            targetEntity = Bookmark.class,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JoinColumn
    private List<Bookmark> bookmarks;

    @JsonIgnore
    @OneToMany(
            targetEntity = Review2.class,
            fetch = FetchType.LAZY)
    @JoinColumn
    private List<Review2> reviews;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(authority);
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return getId();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
