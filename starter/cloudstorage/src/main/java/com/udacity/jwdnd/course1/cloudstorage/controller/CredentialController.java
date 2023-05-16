package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping
    public String createCredential(Model model, Authentication auth, @ModelAttribute Credential credential) {
        credential.setUserid(userService.getUser(auth.getName()).getUserid());
        credentialService.createCredential(credential);
        model.addAttribute("changeSuccessfully", true);
        model.addAttribute("tab", "nav-credentials-tab");
        return "result";
    }

    @PostMapping("/{credentialid}")
    public String deleteCredential(Model model, Authentication auth, @PathVariable Integer credentialid) {
        credentialService.deleteCredential(credentialid,userService.getUser(auth.getName()).getUserid());
        model.addAttribute("changeSuccessfully", true);
        model.addAttribute("tab", "nav-credentials-tab");
        return "result";
    }
}
