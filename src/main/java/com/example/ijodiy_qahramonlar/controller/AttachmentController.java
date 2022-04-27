package com.example.ijodiy_qahramonlar.controller;

import com.example.ijodiy_qahramonlar.dto.ApiResponse;
import com.example.ijodiy_qahramonlar.dto.AttachmentDto;
import com.example.ijodiy_qahramonlar.entity.Attachment;
import com.example.ijodiy_qahramonlar.entity.AttachmentContent;
import com.example.ijodiy_qahramonlar.entity.Category;
import com.example.ijodiy_qahramonlar.repository.AttachmentContentRepository;
import com.example.ijodiy_qahramonlar.repository.AttachmentRepository;
import com.example.ijodiy_qahramonlar.repository.CategoryRepository;
import com.example.ijodiy_qahramonlar.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@RequestMapping("/attachment")
@Controller
public class AttachmentController {
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("message","All Attachment");
        model.addAttribute("attachmentList", attachmentRepository.findAll());
        return "attachment";
    }

    @PostMapping("/upload")
    public String uploadFile( MultipartHttpServletRequest request) throws IOException {
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

                attachment.setFileOriginalName(file.getOriginalFilename());
                attachment.setSize(file.getSize());
                attachment.setContentType(file.getContentType());

                Attachment save = attachmentRepository.save(attachment);

                //file ni byte [] saqlaymiz

                AttachmentContent attachmentContent = new AttachmentContent();
                attachmentContent.setAttachment(save);
                attachmentContent.setAsosiyContent(file.getBytes());

                AttachmentContent save1 = attachmentContentRepository.save(attachmentContent);
                return "Fayl saqlandi. ID si: " + save.getId();
            }
        }
        return "Xatolik";
    }
    @PostMapping("/yuklash")
    public String fileUpload(@RequestParam("file") MultipartFile file,  Model model) throws IOException {
        Attachment attachment = new Attachment();
        String fileName = file.getOriginalFilename();
        attachment.setFileOriginalName(fileName);
        attachment.setName(file.getName());
        attachment.setContentType(file.getContentType());
        attachment.setSize(file.getSize());
        Attachment save = attachmentRepository.save(attachment);
        AttachmentContent attachmentContent=new AttachmentContent();
        attachmentContent.setAsosiyContent(file.getBytes());
        attachmentContent.setAttachment(save);
        attachmentContentRepository.save(attachmentContent);
        model.addAttribute("success", "File Uploaded Successfully!!!");
        return "attachment";

    }

    @PostMapping("/files")
    public ApiResponse uploadFiles(MultipartHttpServletRequest request) {
        return attachmentService.uploadFile(request);
    }

    @GetMapping("/download/{id}")
    public String getFile(@PathVariable Integer id, HttpServletResponse response,Model model) throws IOException {
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
        return "attachment";
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
    public String getById(@PathVariable Integer id){
        Optional<Attachment> byId = attachmentRepository.findById(id);
        return "attachment";
    }

}
