package com.portfolio.my_note.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.portfolio.my_note.model.Note;
import com.portfolio.my_note.model.User;
import com.portfolio.my_note.repository.NoteRepository;

import jakarta.transaction.Transactional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public void saveNoteForUser(User user, String content){
        if (user == null){
            throw new RuntimeException("User is null");
        }

        Note note = new Note();
        note.setContent(content);
        note.setUser(user);

        noteRepository.save(note);

    }

    public void update(int id, String content){
        Optional<Note> optionalNote = noteRepository.findById(id);
        
        if (optionalNote.isPresent()){
            Note note = optionalNote.get();
            note.setContent(content);
            noteRepository.save(note);
        }else {
            throw new RuntimeException("Note not found!");
        }
    }

    @Transactional
    public void delete(int id) {
        try {
            Optional<Note> note = noteRepository.findById(id);
            if (note.isPresent()) {
                noteRepository.delete(note.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
