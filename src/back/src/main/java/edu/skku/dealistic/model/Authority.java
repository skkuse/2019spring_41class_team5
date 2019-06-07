package edu.skku.dealistic.model;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    USER, MANAGER;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
