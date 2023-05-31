package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public String postsCreateForm(Model model){
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    protected User user1 = new User("hunterg7", "hunter@gmail.com", "123");
    protected User user2 = new User("jim1234", "jim@gmail.com", "123");
    protected User user3 = new User("test", "test@test.com", "123");
    protected List<User> users = new ArrayList<>(List.of(user1, user2, user3));

    @PostMapping("/posts/create")
    public String createPosts(@ModelAttribute Post post) {
        Random random = new Random();
        int randomIndex = random.nextInt(users.size());
        User user = users.get(randomIndex);
        System.out.println(usersDao.findByUsername(user));
        if (!usersDao.findByUsername(user)) {
            usersDao.save(user);
        }
        post.setUser(user);
        postsDao.save(post);

        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable long id, Model model){
        model.addAttribute("user", usersDao.findById(id));
        model.addAttribute("post", postsDao.findById(id));
        return "posts/showPost";
    }



}
