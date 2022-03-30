package com.example.ijodiy_qahramonlar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PoemDto {
    private String name;
    private String description;
    private Integer authorId;
    private Integer attachmentId;
}
