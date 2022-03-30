package com.example.ijodiy_qahramonlar.service;

import com.example.ijodiy_qahramonlar.dto.ApiResponse;
import com.example.ijodiy_qahramonlar.dto.CategoryDto;
import com.example.ijodiy_qahramonlar.entity.Category;
import com.example.ijodiy_qahramonlar.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    final CategoryRepository categoryRepository;

    public ApiResponse add(CategoryDto dto) {
        if(categoryRepository.existsByNameIgnoreCase(dto.getName())){
            return new ApiResponse("category already exist!",false);
        }
        Category category=new Category();
        category.setName(dto.getName());
        category.setActive(dto.isActive());
        categoryRepository.save(category);
        return new ApiResponse("Added",true);
    }

    public ApiResponse edit(Integer id, CategoryDto dto) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (!byId.isPresent()) {
            return new ApiResponse("Not found",false);
        }
        if(categoryRepository.existsByNameIgnoreCase(dto.getName())){
            Category category = byId.get();
            category.setName(dto.getName());
            category.setActive(dto.isActive());
            categoryRepository.save(category);
            return new ApiResponse("Edited",true);
        }
        return new ApiResponse("Wrong Option",false);
    }

    public ApiResponse delete(Integer id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (!byId.isPresent()) {
            return new ApiResponse("Not found",false);
        }
        Category category = byId.get();
        categoryRepository.delete(category);
        return new ApiResponse("deleted",true);
    }
}
