package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;
    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    //////////////
    // REGISTER //
    //////////////
    @GetMapping("/register")
    public String showRegisterForm(){
        return "users/register";
    }
    @PostMapping("/register")
    public String registerUser(@RequestParam(name = "username") String username,
                               @RequestParam(name = "email") String email,
                               @RequestParam(name = "password") String password,
                               @RequestParam(name = "confirmPassword") String confirmPassword) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return "redirect:/register";
        } else if (!password.equals(confirmPassword)) {
            return "redirect:/register";
        } else if (userDao.findUserByUsername(username) != null) {
            return "redirect:/register";
        } else {
            password = passwordEncoder.encode(password);
            userDao.save(new User(username, email, password));
            return "redirect:/login";
        }
    }

    ///////////
    // LOGIN //
    ///////////
    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }
    @PostMapping("/login")
    public String login(@RequestParam(name = "username") String username,
                        @RequestParam(name = "password") String password,
                        @RequestParam(name = "confirmPassword") String confirmPassword) {
        User user = userDao.findUserByUsername(username);
        if (user == null) {
            return "redirect:/login";
        } else if (!user.getPassword().equals(password)){
            return "redirect:/login";
        } else {
            return "redirect:/posts";
        }
    }

    /////////////
    // PROFILE //
    /////////////
    @GetMapping("/profile")
    public String showProfile(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();
        user = userDao.findById(userId);
        model.addAttribute("user", user);

        return "users/profile";
    }
}
