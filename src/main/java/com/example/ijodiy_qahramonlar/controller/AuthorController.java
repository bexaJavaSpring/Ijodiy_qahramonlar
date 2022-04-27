package com.example.ijodiy_qahramonlar.controller;

import com.example.ijodiy_qahramonlar.dto.ApiResponse;
import com.example.ijodiy_qahramonlar.dto.AuthorDto;
import com.example.ijodiy_qahramonlar.dto.CategoryDto;
import com.example.ijodiy_qahramonlar.entity.Author;
import com.example.ijodiy_qahramonlar.repository.AttachmentRepository;
import com.example.ijodiy_qahramonlar.repository.AuthorRepository;
import com.example.ijodiy_qahramonlar.repository.RegionRepository;
import com.example.ijodiy_qahramonlar.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/author")
@Controller
@RequiredArgsConstructor
public class AuthorController {
    final AuthorRepository authorRepository;
    final AuthorService authorService;
    final AttachmentRepository attachmentRepository;
    final RegionRepository regionRepository;
    @GetMapping
    public String getAut(Model model) {
        model.addAttribute("message","All Authors");
        model.addAttribute("authorList", authorRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        model.addAttribute("regionList",regionRepository.findAll());
        return "author";
    }

    @GetMapping("/{id}")
    public String getOneAut(@PathVariable Integer id, Model model) {
        Optional<Author> byId = authorRepository.findById(id);
        Author author = byId.get();
        model.addAttribute("author", author);
        return "author";
    }

    @GetMapping("/add")
    public String addAutPage(Model model) {
        model.addAttribute("authorList",authorRepository.findAll());
        model.addAttribute("regionList",regionRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "author";
    }

    @PostMapping("/add")
    public String addAutSave(@ModelAttribute AuthorDto dto,Model model) {
        ApiResponse apiResponse=authorService.add(dto);
        model.addAttribute("message",apiResponse.getMessage());
        model.addAttribute("authorList",authorRepository.findAll());
        model.addAttribute("regionList",regionRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "author";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model) {
        model.addAttribute("author", authorRepository.findById(id).get());
        model.addAttribute("authorList",authorRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        model.addAttribute("regionList",regionRepository.findAll());
        return "author-edit";
    }

    @PostMapping("/edit/{id}")
    public String editAutSave(@PathVariable Integer id, @ModelAttribute AuthorDto dto,Model model) {
        ApiResponse apiResponse=authorService.edit(id, dto);
        model.addAttribute("message",apiResponse.getMessage());
        model.addAttribute("authorList",authorRepository.findAll());
        model.addAttribute("regionList",regionRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "author";
    }

    @GetMapping("/delete/{id}")
    public String deletePage(@PathVariable Integer id,Model model) {
        ApiResponse apiResponse=authorService.delete(id);
        model.addAttribute("message",apiResponse.getMessage());
        model.addAttribute("authorList",authorRepository.findAll());
        model.addAttribute("regionList",regionRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "author";
    }
}
