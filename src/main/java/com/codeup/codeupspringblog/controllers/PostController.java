package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {
    private final PostRepository postsDao;
    private final UserRepository usersDao;
    public PostController(PostRepository postsDao, UserRepository usersDao){
        this.postsDao = postsDao;
        this.usersDao = usersDao;
    }

    @GetMapping("/posts")
    public String posts(Model model){
        model.addAttribute("posts", postsDao.findAll());
        return "posts/index";
    }

    // post mapping for search
    @PostMapping("/posts")
    public String filterPostsBySearch(@RequestParam(name = "searchInput") String search, Model model){
        model.addAttribute("posts", postsDao.searchByTitleLikeOrDescriptionLike(search));
        return "posts/index";
    }


    @GetMapping("/posts/create")
    public String postsCreateForm(){
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPosts(@RequestParam(name = "title") String title, @RequestParam(name = "description") String desc) {
        User user = new User("test3", "test3@test.com", "password");
        usersDao.save(user);
        Post post = new Post(title, desc, user);
        postsDao.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable long id, Model model){
        model.addAttribute("post", postsDao.findById(id));
        System.out.println(id);
        return "posts/showPost";
    }



}
