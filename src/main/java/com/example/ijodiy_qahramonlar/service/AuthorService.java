package com.example.ijodiy_qahramonlar.service;

import com.example.ijodiy_qahramonlar.dto.ApiResponse;
import com.example.ijodiy_qahramonlar.dto.AuthorDto;
import com.example.ijodiy_qahramonlar.entity.Address;
import com.example.ijodiy_qahramonlar.entity.Attachment;
import com.example.ijodiy_qahramonlar.entity.Author;
import com.example.ijodiy_qahramonlar.entity.Region;
import com.example.ijodiy_qahramonlar.repository.AddressRepository;
import com.example.ijodiy_qahramonlar.repository.AttachmentRepository;
import com.example.ijodiy_qahramonlar.repository.AuthorRepository;
import com.example.ijodiy_qahramonlar.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    final AuthorRepository authorRepository;
    final RegionRepository regionRepository;
    final AddressRepository addressRepository;
    final AttachmentRepository attachmentRepository;

    public ApiResponse add(AuthorDto dto) {
        if(authorRepository.existsByFullNameIgnoreCase(dto.getFullName())){
            return new ApiResponse("Author already exist",false);
        }
        Author author=new Author();
        author.setFullName(dto.getFullName());
        author.setAge(dto.getAge());
        Optional<Region> byId = regionRepository.findById(dto.getRegionId());
        author.setRegion(byId.get());
        Optional<Address> byId1 = addressRepository.findById(dto.getAddressId());
        author.setAddress(byId1.get());
        Optional<Attachment> byId2 = attachmentRepository.findById(dto.getAttachmentId());
        author.setAttachment(byId2.get());
        Author save = authorRepository.save(author);
        return new ApiResponse("Added",true,save);
    }

    public ApiResponse edit(Integer id, AuthorDto dto) {
        Optional<Author> byIds = authorRepository.findById(id);
        if (!byIds.isPresent()) {
            return new ApiResponse("Not found",false);
        }
        if(authorRepository.existsByFullNameIgnoreCase(dto.getFullName())){
            Author author = byIds.get();
            author.setFullName(dto.getFullName());
            author.setAge(dto.getAge());
            Optional<Region> byId = regionRepository.findById(dto.getRegionId());
            author.setRegion(byId.get());
            Optional<Address> byId1 = addressRepository.findById(dto.getAddressId());
            author.setAddress(byId1.get());
            Optional<Attachment> byId2 = attachmentRepository.findById(dto.getAttachmentId());
            author.setAttachment(byId2.get());
            authorRepository.save(author);
            return new ApiResponse("Edited",true);
        }
        return new ApiResponse("Wrong operation",false);
    }

    public ApiResponse delete(Integer id) {
        Optional<Author> byId = authorRepository.findById(id);
        if (!byId.isPresent()) {
            return new ApiResponse("Not found",false);
        }
        authorRepository.delete(byId.get());
        return new ApiResponse("Delete",true);

    }
}
