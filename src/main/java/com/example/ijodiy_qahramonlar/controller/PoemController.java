package com.example.ijodiy_qahramonlar.controller;

import com.example.ijodiy_qahramonlar.dto.ApiResponse;
import com.example.ijodiy_qahramonlar.dto.CategoryDto;
import com.example.ijodiy_qahramonlar.dto.PoemDto;
import com.example.ijodiy_qahramonlar.dto.RegionDto;
import com.example.ijodiy_qahramonlar.entity.Poem;
import com.example.ijodiy_qahramonlar.entity.Region;
import com.example.ijodiy_qahramonlar.repository.AuthorRepository;
import com.example.ijodiy_qahramonlar.repository.CategoryRepository;
import com.example.ijodiy_qahramonlar.repository.PoemRepository;
import com.example.ijodiy_qahramonlar.service.CategoryService;
import com.example.ijodiy_qahramonlar.service.PoemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/poem")
@Controller
@RequiredArgsConstructor
public class PoemController {
    final PoemRepository poemRepository;
    final PoemService poemService;
       final AuthorRepository authorRepository;
    @GetMapping
    public String getPoem(Model model) {
        model.addAttribute("message","All Poems");
        model.addAttribute("poemList", poemRepository.findAll());
        model.addAttribute("authorList",authorRepository.findAll());
        return "poem";
    }

    @GetMapping("/{id}")
    public String getOnePoem(@PathVariable Integer id, Model model) {
        Optional<Poem> byId = poemRepository.findById(id);
        Poem poem = byId.get();
        model.addAttribute("poem", poem);
        return "poem";
    }

    @GetMapping("/add")
    public String addPoemPage(Model model) {
        model.addAttribute("poemList",poemRepository.findAll());
        model.addAttribute("authorList",authorRepository.findAll());
        return "poem";
    }

    @PostMapping("/add")
    public String addPoemSave(@ModelAttribute PoemDto dto, Model model) {
        ApiResponse apiResponse=poemService.add(dto);
        model.addAttribute("message",apiResponse.getMessage());
        model.addAttribute("poemList",poemRepository.findAll());
        model.addAttribute("authorList",authorRepository.findAll());
        return "poem";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model) {
        model.addAttribute("poem", poemRepository.findById(id).get());
        model.addAttribute("poemList",poemRepository.findAll());
        model.addAttribute("authorList",authorRepository.findAll());

        return "poem-edit";
    }

    @PostMapping("/edit/{id}")
    public String editPoemSave(@PathVariable Integer id, @ModelAttribute PoemDto dto,Model model) {
        ApiResponse apiResponse=poemService.edit(id, dto);
        model.addAttribute("message",apiResponse.getMessage());
        model.addAttribute("poemList",poemRepository.findAll());
        model.addAttribute("authorList",authorRepository.findAll());
        return "poem";
    }

    @GetMapping("/delete/{id}")
    public String deletePage(@PathVariable Integer id,Model model) {
        ApiResponse apiResponse=poemService.delete(id);
        model.addAttribute("message",apiResponse.getMessage());
        model.addAttribute("poemList",poemRepository.findAll());
        model.addAttribute("authorList",authorRepository.findAll());
        return "poem";
    }
}
