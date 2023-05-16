package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final FileService fileService;
    @Autowired
    private final NoteService noteService;
    @Autowired
    private final CredentialService credentialService;

    public HomeController( UserService userService, FileService fileService, NoteService noteService,
                          CredentialService credentialService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }


    @GetMapping
    public String getHome( Model model, Authentication authentication) {
        String currentUsername = authentication.getName();
        Integer userId=userService.getUser(currentUsername).getUserid();
        model.addAttribute("files", fileService.getAllFiles(userId));
        model.addAttribute("notes", noteService.getAllNotes(userId));
        model.addAttribute("credentials", credentialService.getCredentials(userId));
        model.addAttribute("note", new Note());
        model.addAttribute("credential", new Credential());
        return "home";
    }
}
