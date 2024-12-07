package com.portfolio.my_note.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.portfolio.my_note.model.Note;
import com.portfolio.my_note.model.User;
import com.portfolio.my_note.repository.NoteRepository;
import com.portfolio.my_note.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private NoteRepository noteRepository;

    @Autowired 
    private PasswordEncoder encoder;

    public void save(User user){

        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);

    }

    public User getUser(String username){
        User user = userRepository.findByUsername(username);
        if (username.equals(user.getUsername())){
            return user;
        }else {
            throw new RuntimeException("This username not found!");
        }
    }

    public boolean checkUsername(String username){
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    public List<Note> findNotesByUser(User user){
        return noteRepository.findByUser(user);
    }

}
