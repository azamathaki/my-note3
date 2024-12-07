package com.portfolio.my_note.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.my_note.model.Note;
import com.portfolio.my_note.model.User;

public interface NoteRepository extends JpaRepository<Note, Integer>{

    List<Note> findByUser(User user);
    Optional<Note> findById(int id);
    void deleteById(int id);
    
}
