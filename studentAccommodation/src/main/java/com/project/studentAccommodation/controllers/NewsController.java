package com.project.studentAccommodation.controllers;

import com.project.studentAccommodation.models.NewsPost;
import com.project.studentAccommodation.repositories.NewsPostRepository;
import com.project.studentAccommodation.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
public class NewsController {

    @Autowired
    private NewsPostRepository newsPostRepository;
    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/news")
    public String showNewsPage(HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");
        if(!Objects.equals(userRole, "ADMIN"))
            return "redirect:/";
        else return "news";
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

    @PostMapping("/update-news")
    public ResponseEntity<String> updateNews(@RequestParam Long postId, @RequestParam String title, @RequestParam String content) {
        NewsPost newsPost = newsPostRepository.findById(postId).orElse(null);
        if (newsPost != null) {
            newsPost.setTitle(title);
            newsPost.setContent(content);
            newsPostRepository.save(newsPost);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
