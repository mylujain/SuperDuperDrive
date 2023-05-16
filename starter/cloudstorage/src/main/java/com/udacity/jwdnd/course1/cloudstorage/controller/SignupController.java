package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller()
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping()
    public String signupView(@ModelAttribute User user, Model model) {
        return "signup";
    }

    @PostMapping()
    public String userSignup(Model model, @ModelAttribute User user, RedirectAttributes redirectAttributes) {
int checkAvailability=userService.usernameAvailability(user.getUsername());
        if (checkAvailability==1) {
        model.addAttribute("signupError", "This username is already exists.");
        return "signup";
    }

        userService.createUser(user);
        redirectAttributes.addFlashAttribute("signupSuccess", true);
        return "redirect:/login";
    }



    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

}

