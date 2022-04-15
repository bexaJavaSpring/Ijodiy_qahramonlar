package com.example.ijodiy_qahramonlar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorDto {
    private String fullName;
    private String district;
    private Integer regionId;
    private Integer attachmentId;
    private String regions;
    private String village;

}
