package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private UserService userService;
    @Autowired
    private NoteService noteService;


    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping
    public String createNote(Model model, Authentication auth, @ModelAttribute Note note) {
        note.setUserid(userService.getUser(auth.getName()).getUserid());
            noteService.createNote(note);
            model.addAttribute("changeSuccessfully", true);
            model.addAttribute("tab", "nav-notes-tab");
        return "result";
    }

    @PostMapping("/{noteid}")
    public String deleteNote(Model model, Authentication auth, @PathVariable Integer noteid) {
            noteService.deleteNote(noteid, userService.getUser(auth.getName()).getUserid());
            model.addAttribute("changeSuccessfully", true);
            model.addAttribute("tab", "nav-notes-tab");
        return "result";
    }
}
