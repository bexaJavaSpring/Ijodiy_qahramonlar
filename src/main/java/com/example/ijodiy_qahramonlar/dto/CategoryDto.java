package com.example.ijodiy_qahramonlar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {
    private String name;
    private boolean active;
    private Integer attachmentId;
}
