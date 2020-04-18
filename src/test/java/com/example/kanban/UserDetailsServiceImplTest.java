package com.example.kanban;


import com.example.kanban.entities.user.User;
import com.example.kanban.entities.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes={
        KanbanApplication.class,
        UserDetailsServiceImpl.class,
        UserRepository.class,
        User.class,
        })
class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testLoadUserByUsername() {

        List<String> emailList = userRepository.getAllEmails();

        for(String email : emailList) {
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
            assertTrue(userDetails instanceof UserDetails);
        }

        assertThrows(UsernameNotFoundException.class, () ->
            userDetailsServiceImpl.loadUserByUsername("notExisting@Email.com"));
    }
}