package com.example.ijodiy_qahramonlar.controller;

import com.example.ijodiy_qahramonlar.dto.ApiResponse;
import com.example.ijodiy_qahramonlar.entity.Attachment;
import com.example.ijodiy_qahramonlar.entity.AttachmentContent;
import com.example.ijodiy_qahramonlar.repository.AttachmentContentRepository;
import com.example.ijodiy_qahramonlar.repository.AttachmentRepository;
import com.example.ijodiy_qahramonlar.service.AttachmentService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RequestMapping("/attachment")
@RestController
public class AttachmentController {
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @GetMapping("/info")
    public List<Attachment> getAll() {
        List<Attachment> all = attachmentRepository.findAll();
        return all;
    }

    @PostMapping("/upload")
    public String uploadFile(MultipartHttpServletRequest request) throws IOException {

        Iterator<String> fileNames = request.getFileNames();

        while (fileNames.hasNext()) {
            String next = fileNames.next();
            MultipartFile file = request.getFile(next);

            if (file != null) {
                //file haqida malumot olish uchun
                String originalFilename = file.getOriginalFilename();
                long size = file.getSize();
                String contentType = file.getContentType();
                Attachment attachment = new Attachment();

                attachment.setFileOriginalName(originalFilename);
                attachment.setSize(size);
                attachment.setContentType(contentType);

                Attachment save = attachmentRepository.save(attachment);

                //file ni byte [] saqlaymiz

                AttachmentContent attachmentContent = new AttachmentContent();
                attachmentContent.setAttachment(save);
                attachmentContent.setAsosiyContent(file.getBytes());

                AttachmentContent save1 = attachmentContentRepository.save(attachmentContent);
                return "Fayl saqlandi. ID si: " + save.getId();
            }
        }
        return "xatolik";
    }

    @PostMapping("/files")
    public ApiResponse uploadFiles(MultipartHttpServletRequest request) {
        return attachmentService.uploadFile(request);
    }

    @GetMapping("/download/{id}")
    public void getFile(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachment> byId = attachmentRepository.findById(id);
        if (byId.isPresent()) {
            Attachment attachment = byId.get();
            Optional<AttachmentContent> byAttachment = attachmentContentRepository.findByAttachment(attachment);
            if(byAttachment.isPresent()){
                AttachmentContent attachmentContent = byAttachment.get();
                response.setHeader("Content-Disposition","attachment; filename:"+ attachment.getName());
                response.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachmentContent.getAsosiyContent(), response.getOutputStream());
            }
        }
    }
    private static final String uploadDirectory = "filelar";
    @GetMapping("dowloadSystem/{id}")
    public void getFileTofileSystem(@PathVariable Integer id,HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()){
            Attachment attachment = optionalAttachment.get();
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + attachment.getName());
            response.setContentType(attachment.getContentType());

            FileInputStream fileInputStream=new FileInputStream(uploadDirectory+"/"+attachment.getName());

            FileCopyUtils.copy(fileInputStream, response.getOutputStream());
        }
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        Optional<Attachment> byId = attachmentRepository.findById(id);
        return ResponseEntity.ok().body(byId.get());
    }

}
