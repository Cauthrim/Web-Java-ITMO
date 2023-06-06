package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping("/post/{id}")
    public String post(Model model, @PathVariable String id) {
        model.addAttribute("comment", new Comment());
        long postId;
        try {
            postId = Long.parseLong(id);
            model.addAttribute("post", postService.findById(postId));
        } catch (Exception ignored) {
        }
        return "PostPage";
    }

    @Guest
    @GetMapping("/post/")
    public String post() {
        return "PostPage";
    }

    @PostMapping("/post/{id}")
    public String writeCommentPost(@Valid @ModelAttribute("comment") Comment comment,
                                   BindingResult bindingResult,
                                   @PathVariable String id,
                                   HttpSession httpSession,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            long postId;
            try {
                postId = Long.parseLong(id);
                model.addAttribute("post", postService.findById(postId));
            } catch (Exception ignored) {}
            return "PostPage";
        }

        long postId;
        try {
            postId = Long.parseLong(id);
            postService.writeComment(postService.findById(postId), getUser(httpSession), comment);
            putMessage(httpSession, "You added new Comment");
        } catch (Exception ignored) {
        }

        return "redirect:/post/" + id;
    }
}
