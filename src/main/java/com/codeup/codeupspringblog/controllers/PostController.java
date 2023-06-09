package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Controller
public class PostController {
    private final PostRepository postsDao;
    private final UserRepository usersDao;
    public PostController(PostRepository postsDao, UserRepository usersDao){
        this.postsDao = postsDao;
        this.usersDao = usersDao;
    }

    ///////////////////////
    // DISPLAY ALL POSTS //
    ///////////////////////
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

    /////////////////
    // CREATE POST //
    /////////////////
    @GetMapping("/posts/create")
    public String postsCreateForm(Model model){
        model.addAttribute("post", new Post());
        return "posts/create";
    }
    @PostMapping("/posts/create")
    public String createPosts(@ModelAttribute Post post) {
        List<User> users = usersDao.findAll();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();
        user = usersDao.findById(userId);
        post.setUser(user);
        postsDao.save(post);

        return "redirect:/posts";
    }

    //////////////////////
    // DISPLAY ONE POST //
    //////////////////////
    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable long id, Model model){
        Post post = postsDao.findById(id);
        User user = usersDao.findById(post.getUser().getId());
        model.addAttribute("user", user);
        model.addAttribute("post", post);
        return "posts/showPost";
    }

    //////////////////
    // DELETE POSTS //
    //////////////////
    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable long id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postsDao.findById(id);

        if (user.getId() == post.getUser().getId()) {
            postsDao.deleteById(id);
        }
        return "redirect:/posts";
    }

    ////////////////
    // EDIT POSTS //
    ////////////////
    @GetMapping("/posts/{id}/edit")
    public String viewEditForm(@PathVariable long id, Model model){
        Post post = postsDao.findById(id);
        if (post == null){
            return "redirect:/posts";
        }

        model.addAttribute("post", post);
        return "posts/edit";
    }
    @PostMapping("/posts/{id}/edit")
    public String submitEditForm(@ModelAttribute Post post, @PathVariable long id){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post oldPost = postsDao.findById(id);
        User user = oldPost.getUser();

        if (currentUser.getId() == user.getId()){
            post.setUser(user);
            post.setId(id);
            postsDao.save(post);
        }

        return "redirect:/posts";
    }

}
