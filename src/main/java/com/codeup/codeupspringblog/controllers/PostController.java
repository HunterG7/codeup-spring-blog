package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    @GetMapping("/posts")
    public String posts(Model model){
        Post post1 = new Post("Hunter's Post", "This is the body of Hunter's post.");
        Post post2 = new Post("Dylan's Post", "This is the body of Dylan's post.");
        Post post3 = new Post("Ryan's Post", "This is the body of Ryan's post.");
        ArrayList<Post> posts = new ArrayList<>(List.of(post1, post2, post3));

        model.addAttribute("posts", posts);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String postsFromId(@PathVariable int id, Model model){
        Post post = new Post("Hunter's Post", "This is the body of Hunter's post with an id of " + id + ".");

        model.addAttribute("post", post);
        return "posts/show";
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
