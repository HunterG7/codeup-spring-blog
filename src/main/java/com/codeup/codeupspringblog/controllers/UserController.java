package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    private final UserRepository userDao;
    private UserController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/profile")
    public String showProfile() {
        return "users/profile";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(){
        return "users/register";
    }
}
