package com.example.ijodiy_qahramonlar.controller;

import com.example.ijodiy_qahramonlar.bot.IjodBot;
import com.example.ijodiy_qahramonlar.dto.ApiResponse;
import com.example.ijodiy_qahramonlar.dto.PostDto;
import com.example.ijodiy_qahramonlar.dto.PostDto;
import com.example.ijodiy_qahramonlar.entity.Post;
import com.example.ijodiy_qahramonlar.repository.AttachmentRepository;
import com.example.ijodiy_qahramonlar.repository.PostRepository;
import com.example.ijodiy_qahramonlar.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/post")
@Controller
@RequiredArgsConstructor
public class PostController {
    final PostRepository postRepository;
    final PostService postService;
    final AttachmentRepository attachmentRepository;
    @GetMapping
    public String getPost(Model model) {
        model.addAttribute("message","All Post");
        model.addAttribute("postList", postRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "post";
    }

    @GetMapping("/{id}")
    public String getOnePost(@PathVariable Integer id, Model model) {
        Optional<Post> byId = postRepository.findById(id);
        Post post = byId.get();
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/add")
    public String addPostPage(Model model) {
        model.addAttribute("postList",postRepository.findAll());
        return "post";
    }

    @PostMapping("/add")
    public String addPostSave(@ModelAttribute PostDto dto, Model model) {
        ApiResponse apiResponse=postService.add(dto);
        model.addAttribute("message",apiResponse.getMessage());
        model.addAttribute("postList",postRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "post";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model) {
        model.addAttribute("post", postRepository.findById(id).get());
        model.addAttribute("postList",postRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());

        return "post-edit";
    }

    @PostMapping("/edit/{id}")
    public String editPostSave(@PathVariable Integer id, @ModelAttribute PostDto dto,Model model) {
        ApiResponse apiResponse=postService.edit(id, dto);
        model.addAttribute("message",apiResponse.getMessage());
        model.addAttribute("postList",postRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "post";
    }

    @GetMapping("/delete/{id}")
    public String deletePage(@PathVariable Integer id,Model model) {
        ApiResponse apiResponse=postService.delete(id);
        model.addAttribute("message",apiResponse.getMessage());
        model.addAttribute("postList",postRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "post";
    }

}
