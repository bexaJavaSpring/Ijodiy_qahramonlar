package com.example.ijodiy_qahramonlar.controller;

import com.example.ijodiy_qahramonlar.dto.ApiResponse;
import com.example.ijodiy_qahramonlar.dto.CategoryDto;
import com.example.ijodiy_qahramonlar.entity.Attachment;
import com.example.ijodiy_qahramonlar.entity.Category;
import com.example.ijodiy_qahramonlar.repository.AttachmentRepository;
import com.example.ijodiy_qahramonlar.repository.CategoryRepository;
import com.example.ijodiy_qahramonlar.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/category")
@Controller
@RequiredArgsConstructor
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    AttachmentRepository attachmentRepository;
    @GetMapping
    public String getCat(Model model) {
        model.addAttribute("message","All Categories");
        model.addAttribute("categoryList", categoryRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "category";
    }

    @GetMapping("/{id}")
    public String getOneCat(@PathVariable Integer id, Model model) {
        Optional<Category> byId = categoryRepository.findById(id);
        Category category = byId.get();
        model.addAttribute("category", category);
        return "category";
    }


    @GetMapping("/add")
    public String addCatPage(Model model) {
        model.addAttribute("categoryList",categoryRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "category";
    }

    @PostMapping("/add")
    public String addCatSave(@ModelAttribute CategoryDto dto,Model model) {
        ApiResponse apiResponse=categoryService.add(dto);
        model.addAttribute("message",apiResponse.getMessage());
        model.addAttribute("categoryList",categoryRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "category";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryRepository.findById(id).get());
        model.addAttribute("categoryList",categoryRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "category-edit";
    }

    @PostMapping("/edit/{id}")
    public String editCatSave(@PathVariable Integer id, @ModelAttribute CategoryDto dto,Model model) {
        ApiResponse apiResponse=categoryService.edit(id, dto);
        model.addAttribute("message",apiResponse.getMessage());
        model.addAttribute("categoryList",categoryRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "category";
    }

    @GetMapping("/delete/{id}")
    public String deletePage(@PathVariable Integer id,Model model) {
        ApiResponse apiResponse=categoryService.delete(id);
        model.addAttribute("message",apiResponse.getMessage());
        model.addAttribute("categoryList",categoryRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "category";
    }
}
