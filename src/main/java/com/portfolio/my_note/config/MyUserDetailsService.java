package com.portfolio.my_note.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.portfolio.my_note.model.User;
import com.portfolio.my_note.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null){
            System.out.println("User not found!");
        }

        session.setAttribute("user", user);

        return new UserPrincipal(user);

    }
    
}
