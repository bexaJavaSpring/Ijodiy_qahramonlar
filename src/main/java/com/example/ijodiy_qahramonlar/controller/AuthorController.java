package com.example.ijodiy_qahramonlar.controller;

import com.example.ijodiy_qahramonlar.dto.ApiResponse;
import com.example.ijodiy_qahramonlar.dto.AuthorDto;
import com.example.ijodiy_qahramonlar.dto.CategoryDto;
import com.example.ijodiy_qahramonlar.repository.AuthorRepository;
import com.example.ijodiy_qahramonlar.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/author")
@RestController
@RequiredArgsConstructor
public class AuthorController {
    final AuthorRepository authorRepository;
    final AuthorService authorService;

    @GetMapping
    public HttpEntity<?> getAll(){
        return ResponseEntity.ok().body(authorRepository.findAll());
    }
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody AuthorDto dto){
        ApiResponse apiResponse=authorService.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id,@Valid @RequestBody AuthorDto dto){
        ApiResponse apiResponse=authorService.edit(id,dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse=authorService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?204:404).body(apiResponse);
    }
}
