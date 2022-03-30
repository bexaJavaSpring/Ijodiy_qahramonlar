package com.example.ijodiy_qahramonlar.controller;

import com.example.ijodiy_qahramonlar.dto.ApiResponse;
import com.example.ijodiy_qahramonlar.dto.CategoryDto;
import com.example.ijodiy_qahramonlar.dto.PoemDto;
import com.example.ijodiy_qahramonlar.repository.CategoryRepository;
import com.example.ijodiy_qahramonlar.repository.PoemRepository;
import com.example.ijodiy_qahramonlar.service.CategoryService;
import com.example.ijodiy_qahramonlar.service.PoemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@RequestMapping("/poem")
@RestController
@RequiredArgsConstructor
public class PoemController {
    final PoemRepository poemRepository;
    final PoemService poemService;

    @GetMapping
    public HttpEntity<?> getAll(){
        return ResponseEntity.ok().body(poemRepository.findAll());
    }
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody PoemDto dto){
        ApiResponse apiResponse=poemService.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody PoemDto dto){
        ApiResponse apiResponse=poemService.edit(id,dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse=poemService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?204:404).body(apiResponse);
    }
}
