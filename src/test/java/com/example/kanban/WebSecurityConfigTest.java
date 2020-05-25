package com.example.kanban;

import org.junit.jupiter.api.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.junit.jupiter.api.Assertions.*;


class WebSecurityConfigTest {

    private static WebSecurityConfig webSecurityConfig;
    private AuthenticationManagerBuilder auth;
    private HttpSecurity http;

    @BeforeAll
    static void setup() {
        webSecurityConfig = new WebSecurityConfig();
    }

    @Test
    void testAuthProvider() {
        DaoAuthenticationProvider authProvider = webSecurityConfig.authProvider();
        assertTrue(authProvider instanceof DaoAuthenticationProvider);
    }

    @Test
    void testConfigure(){
        assertThrows(Exception.class, () -> {
            webSecurityConfig.configure(auth);});
        assertThrows(Exception.class, () -> {
            webSecurityConfig.configure(http);});
    }

    @Test
    void testPasswordEncoder(){
        PasswordEncoder passwordEncoder = webSecurityConfig.passwordEncoder();
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }
}