package com.example.kanban.entities.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserType implements GrantedAuthority {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");
    private String role;

    UserType(String role){
        this.role = role;
    }
    @Override
    public String getAuthority() {
        return this.role;
    }
}
