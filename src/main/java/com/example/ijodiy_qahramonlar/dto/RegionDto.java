package com.example.ijodiy_qahramonlar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegionDto {
  private String name;
  private Integer categoryId;
  private Integer attachmentId;
}
