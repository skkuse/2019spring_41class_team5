package edu.skku.dealistic.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * User Authority
 *
 * @author Junhyun Kim
 */
public enum Authority implements GrantedAuthority {
    USER, MANAGER;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
