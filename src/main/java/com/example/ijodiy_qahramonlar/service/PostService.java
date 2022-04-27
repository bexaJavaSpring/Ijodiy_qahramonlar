package com.example.ijodiy_qahramonlar.service;

import com.example.ijodiy_qahramonlar.bot.BotService;
import com.example.ijodiy_qahramonlar.bot.IjodBot;
import com.example.ijodiy_qahramonlar.controller.BotController;
import com.example.ijodiy_qahramonlar.dto.ApiResponse;
import com.example.ijodiy_qahramonlar.dto.PostDto;
import com.example.ijodiy_qahramonlar.entity.Attachment;
import com.example.ijodiy_qahramonlar.entity.Post;
import com.example.ijodiy_qahramonlar.entity.User;
import com.example.ijodiy_qahramonlar.repository.AttachmentRepository;
import com.example.ijodiy_qahramonlar.repository.PostRepository;
import com.example.ijodiy_qahramonlar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    final BotController botController;
    final BotService botService;
    final PostRepository postRepository;
    final AttachmentRepository attachmentRepository;
    final UserRepository userRepository;
    public ApiResponse add(PostDto dto) {
        if(postRepository.existsByDescription(dto.getDescription())){
            return new ApiResponse("Already exist",false);
        }
        Post post=new Post();
        post.setDescription(dto.getDescription());
        Optional<Attachment> byId = attachmentRepository.findById(dto.getAttachmentId());
        post.setAttachment(byId.get());
        Post save = postRepository.save(post);
        botController.sendMessage(save);
        return new ApiResponse("Added",true,save);
    }

    public ApiResponse edit(Integer id, PostDto dto) {
        Optional<Post> byId = postRepository.findById(id);
        if (!byId.isPresent()) {
            return new ApiResponse("Not found",false);
        }
        Post post = byId.get();
        post.setDescription(dto.getDescription());
        post.setCreatedDate(dto.getCratedDate());
        Optional<Attachment> byId1 = attachmentRepository.findById(dto.getAttachmentId());
        post.setAttachment(byId1.get());
        Post save = postRepository.save(post);
        return new ApiResponse("Edited",true,save);
    }

    public ApiResponse delete(Integer id) {
        Optional<Post> byId = postRepository.findById(id);
        if (!byId.isPresent()) {
            return new ApiResponse("Not found",false);
        }
        Post post = byId.get();
        postRepository.delete(post);
        return new ApiResponse("Deleted",true);
    }
}
