package com.example.ijodiy_qahramonlar.controller;

import com.example.ijodiy_qahramonlar.dto.ApiResponse;
import com.example.ijodiy_qahramonlar.dto.CategoryDto;
import com.example.ijodiy_qahramonlar.dto.RegionDto;
import com.example.ijodiy_qahramonlar.entity.Category;
import com.example.ijodiy_qahramonlar.entity.Region;
import com.example.ijodiy_qahramonlar.repository.AttachmentRepository;
import com.example.ijodiy_qahramonlar.repository.CategoryRepository;
import com.example.ijodiy_qahramonlar.repository.RegionRepository;
import com.example.ijodiy_qahramonlar.service.CategoryService;
import com.example.ijodiy_qahramonlar.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/region")
@Controller
@RequiredArgsConstructor
public class RegionController {
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    RegionService regionService;

    @Autowired
    AttachmentRepository attachmentRepository;
    @GetMapping
    public String getReg(Model model) {
        model.addAttribute("message","All Regions");
        model.addAttribute("regionList", regionRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "region";
    }

    @GetMapping("/{id}")
    public String getOneReg(@PathVariable Integer id, Model model) {
        Optional<Region> byId = regionRepository.findById(id);
        Region region = byId.get();
        model.addAttribute("region", region);
        return "region";
    }

    @GetMapping("/add")
    public String addRegPage(Model model) {
        model.addAttribute("regionList",regionRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "region";
    }

    @PostMapping("/add")
    public String addCatSave(@ModelAttribute RegionDto dto,Model model) {
        ApiResponse apiResponse=regionService.add(dto);
        model.addAttribute("message",apiResponse.getMessage());
        model.addAttribute("regionList",regionRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "region";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model) {
        model.addAttribute("region", regionRepository.findById(id).get());
        model.addAttribute("regionList",regionRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "region-edit";
    }

    @PostMapping("/edit/{id}")
    public String editCatSave(@PathVariable Integer id, @ModelAttribute RegionDto dto,Model model) {
        ApiResponse apiResponse=regionService.edit(id, dto);
        model.addAttribute("message",apiResponse.getMessage());
        model.addAttribute("regionList",regionRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "region";
    }

    @GetMapping("/delete/{id}")
    public String deletePage(@PathVariable Integer id,Model model) {
        ApiResponse apiResponse=regionService.delete(id);
        model.addAttribute("message",apiResponse.getMessage());
        model.addAttribute("regionList",regionRepository.findAll());
        model.addAttribute("attachmentList",attachmentRepository.findAll());
        return "region";
    }
}
