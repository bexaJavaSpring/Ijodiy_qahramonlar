package com.example.ijodiy_qahramonlar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorDto {
    private String fullName;
    private Integer age;
    private Integer addressId;
    private Integer regionId;
    private Integer attachmentId;
}
