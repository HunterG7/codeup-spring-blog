package com.codeup.codeupspringblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {
    @GetMapping("/posts")
    @ResponseBody
    public String posts(){
        return "posts index page";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String postsFromId(@PathVariable int id){
        return "view an individual post with id of " + id;
    }

    @GetMapping(path = "/posts/create")
    @ResponseBody
    public String createPosts(){
        return "view form for creating a post";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String postsCreateForm(){
        return "create a new post";
    }
}
