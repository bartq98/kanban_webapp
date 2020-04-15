package com.example.kanban.entities.user;

import org.springframework.security.core.GrantedAuthority;
import org.thymeleaf.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public enum UserType implements GrantedAuthority{
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private List<String> roles;

    UserType(String... roles){
        this.roles = Arrays.asList(roles);
    }

    @Override
    public String getAuthority() {
        return StringUtils.join(this.roles, ",");
    }
}
