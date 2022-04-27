package com.example.ijodiy_qahramonlar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {
    private String description;
    private Integer attachmentId;
    private Date cratedDate;

}
