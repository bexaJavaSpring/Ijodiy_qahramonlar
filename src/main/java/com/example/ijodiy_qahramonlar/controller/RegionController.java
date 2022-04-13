//package com.example.ijodiy_qahramonlar.controller;
//
//import com.example.ijodiy_qahramonlar.dto.ApiResponse;
//import com.example.ijodiy_qahramonlar.dto.CategoryDto;
//import com.example.ijodiy_qahramonlar.dto.RegionDto;
//import com.example.ijodiy_qahramonlar.repository.CategoryRepository;
//import com.example.ijodiy_qahramonlar.repository.RegionRepository;
//import com.example.ijodiy_qahramonlar.service.CategoryService;
//import com.example.ijodiy_qahramonlar.service.RegionService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//@RequestMapping("/region")
//@RestController
//@RequiredArgsConstructor
//public class RegionController {
//    final RegionRepository regionRepository;
//    final RegionService regionService;
//
//    @GetMapping
//    public HttpEntity<?> getAll(){
//        return ResponseEntity.ok().body(regionRepository.findAll());
//    }
//    @PostMapping
//    public HttpEntity<?> add(@Valid @RequestBody RegionDto dto){
//        ApiResponse apiResponse=regionService.add(dto);
//        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
//    }
//    @PutMapping("/{id}")
//    public HttpEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody RegionDto dto){
//        ApiResponse apiResponse=regionService.edit(id,dto);
//        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
//    }
//    @DeleteMapping("/{id}")
//    public HttpEntity<?> delete(@PathVariable Integer id){
//        ApiResponse apiResponse=regionService.delete(id);
//        return ResponseEntity.status(apiResponse.isSuccess()?204:404).body(apiResponse);
//    }
//}
