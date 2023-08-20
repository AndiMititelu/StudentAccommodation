package com.project.studentAccommodation.controllers;

import com.project.studentAccommodation.models.NewsPost;
import com.project.studentAccommodation.repositories.NewsPostRepository;
import com.project.studentAccommodation.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class NewsController {

    @Autowired
    private NewsPostRepository newsPostRepository;
    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/news")
    public String showNewsPage() {
        return "news";
    }
    @PostMapping("/save-news")
    public String saveNews(@ModelAttribute NewsPost newsPost, @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String fileName = fileStorageService.storeFile(file);
            newsPost.setFileName(fileName);
            newsPost.setContent(newsPost.getContent());
            newsPost.setTitle(newsPost.getTitle());
            newsPostRepository.save(newsPost);
            return "redirect:/"; // Redirect to home page
        }
        return "redirect:/news"; //
    }



}
