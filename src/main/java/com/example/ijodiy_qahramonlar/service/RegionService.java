package com.example.ijodiy_qahramonlar.service;

import com.example.ijodiy_qahramonlar.dto.ApiResponse;
import com.example.ijodiy_qahramonlar.dto.RegionDto;
import com.example.ijodiy_qahramonlar.entity.Attachment;
import com.example.ijodiy_qahramonlar.entity.Category;
import com.example.ijodiy_qahramonlar.entity.Region;
import com.example.ijodiy_qahramonlar.repository.AttachmentRepository;
import com.example.ijodiy_qahramonlar.repository.CategoryRepository;
import com.example.ijodiy_qahramonlar.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionService {

    final RegionRepository regionRepository;
    final CategoryRepository categoryRepository;
    final AttachmentRepository attachmentRepository;

    public ApiResponse add(RegionDto dto) {
        if (regionRepository.existsByNameIgnoreCase(dto.getName())) {
            return new ApiResponse("This region already exist", false);
        }
        Region region = new Region();
        region.setName(dto.getName());
        Optional<Attachment> byId1 = attachmentRepository.findById(dto.getAttachmentId());
        region.setAttachment(byId1.get());
        Region save = regionRepository.save(region);
        return new ApiResponse("Added", true, save);
    }

    public ApiResponse edit(Integer id, RegionDto dto) {
        Optional<Region> byId = regionRepository.findById(id);
        if (!byId.isPresent()) {
            return new ApiResponse("Not found", false);
        }
        if (regionRepository.existsByNameIgnoreCase(dto.getName())) {
            Region region = byId.get();
            region.setName(dto.getName());
            Optional<Attachment> byId2 = attachmentRepository.findById(dto.getAttachmentId());
            region.setAttachment(byId2.get());
            return new ApiResponse("edited", true);
        }
        return new ApiResponse("Wrong operation!", false);
    }

    public ApiResponse delete(Integer id) {
        Optional<Region> byId = regionRepository.findById(id);
        if (!byId.isPresent()) {
            return new ApiResponse("Not found",false);
        }
        regionRepository.delete(byId.get());
        return new ApiResponse("Deleted",true);
    }
}
