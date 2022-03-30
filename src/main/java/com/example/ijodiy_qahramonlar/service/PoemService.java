package com.example.ijodiy_qahramonlar.service;

import com.example.ijodiy_qahramonlar.dto.ApiResponse;
import com.example.ijodiy_qahramonlar.dto.PoemDto;
import com.example.ijodiy_qahramonlar.entity.Attachment;
import com.example.ijodiy_qahramonlar.entity.Author;
import com.example.ijodiy_qahramonlar.entity.Poem;
import com.example.ijodiy_qahramonlar.repository.AttachmentRepository;
import com.example.ijodiy_qahramonlar.repository.AuthorRepository;
import com.example.ijodiy_qahramonlar.repository.PoemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PoemService {

    final PoemRepository poemRepository;
   final AttachmentRepository attachmentRepository;
   final AuthorRepository authorRepository;

    public ApiResponse add(PoemDto dto) {
        Poem poem=new Poem();
        poem.setName(dto.getName());
        poem.setDescription(dto.getDescription());
        Optional<Attachment> byId = attachmentRepository.findById(dto.getAttachmentId());
        poem.setAttachment(byId.get());
        Optional<Author> byId1 = authorRepository.findById(dto.getAuthorId());
        poem.setAuthor(byId1.get());
        Poem save = poemRepository.save(poem);
        return new ApiResponse("Added",true,save);
    }

    public ApiResponse edit(Integer id, PoemDto dto) {
        Optional<Poem> byIds = poemRepository.findById(id);
        if (!byIds.isPresent()) {
            return new ApiResponse("Not found",false);
        }
        if(poemRepository.existsByNameIgnoreCase(dto.getName())){
            Poem poem = byIds.get();
            poem.setName(dto.getName());
            poem.setDescription(dto.getDescription());
            Optional<Attachment> byId = attachmentRepository.findById(dto.getAttachmentId());
            poem.setAttachment(byId.get());
            Optional<Author> byId1 = authorRepository.findById(dto.getAuthorId());
            poem.setAuthor(byId1.get());
            Poem save = poemRepository.save(poem);
            return new ApiResponse("Edited",true,save);
        }
        return new ApiResponse("Not found",false);
    }

    public ApiResponse delete(Integer id) {
        Optional<Poem> byId = poemRepository.findById(id);
        if (!byId.isPresent()) {
            return new ApiResponse("Not found",false);
        }
        poemRepository.delete(byId.get());
        return new ApiResponse("Delete",true);
    }
}
