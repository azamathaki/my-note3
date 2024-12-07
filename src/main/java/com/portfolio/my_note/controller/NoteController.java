package com.portfolio.my_note.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.portfolio.my_note.model.Note;
import com.portfolio.my_note.model.User;
import com.portfolio.my_note.service.NoteService;
import com.portfolio.my_note.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
public class NoteController {
    

    @Autowired
    private UserService userService;

    @Autowired 
    private NoteService noteService;

    @GetMapping()
    public String getNotes(Model model,
                            HttpSession session){

        User userSession = (User)session.getAttribute("user");

        String message;
        if (userSession != null){
            User user = userService.getUser(userSession.getUsername());
            List<Note> notes = userService.findNotesByUser(user);

            if (notes == null || notes.isEmpty()){
                message = "No notes saved yet.";
            }else {
                model.addAttribute("notes", notes);
                message = "There are notes in db!";
            }

        }else {
            throw new RuntimeException("user is null! (in NoteController.java)");
        }

        model.addAttribute("message", message);

        return "home";
    }

    @GetMapping("/edit")
    public String getEditing(Model model, HttpSession session) {
        User userSession = (User) session.getAttribute("user");
        if (userSession != null) {

            User user = userService.getUser(userSession.getUsername());
            List<Note> notes = userService.findNotesByUser(user);
            
            model.addAttribute("notes", notes);
        } else {
        }

        return "edit";
    }


    @PostMapping("/handleEdit")
    public String handleEdit(@RequestParam("id") int id, @RequestParam("note") String content){

        noteService.update(id, content);
        return "redirect:/home/edit";
    }

    @PostMapping("/delete")
    public String postDelete(@RequestParam("id") int id) {
        noteService.delete(id);
        return "redirect:/home/edit";
    }


    @GetMapping("/write")
    public String getWrite(){
        return "write";
    }

    @PostMapping("/postingNote")
    public String postNote(@RequestParam("note")String note, HttpSession session){

        User user = (User)session.getAttribute("user");
        if (user == null) {
            throw new RuntimeException("User not logged in.");
        }
        noteService.saveNoteForUser(user,note);

        return "redirect:/home";
    }

}
