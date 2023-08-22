package com.project.studentAccommodation.controllers;

import com.project.studentAccommodation.models.NewsPost;
import com.project.studentAccommodation.repositories.NewsPostRepository;
import com.project.studentAccommodation.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    NewsPostRepository newsPostRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @GetMapping("/")
    public String showHomePage(Model model) {
        List<NewsPost> newsPosts = newsPostRepository.findAll();
        model.addAttribute("newsPosts", newsPosts);
        return "index";
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        // Load file as Resource from your storage location
        Resource resource = fileStorageService.loadFile(fileName); // Replace with your file storage logic

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // Handle or log any exceptions if needed
        }

        // If content type is unknown, set it to a default value
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/edit-news/{postId}")
    public String showEditNewsPage(@PathVariable Long postId, HttpServletRequest request, Model model) {
        String userRole = (String) request.getSession().getAttribute("userRole");
        if (!"ADMIN".equals(userRole)) {
            return "redirect:/"; // Redirect to news homepage if not an admin
        }

        NewsPost newsPost = newsPostRepository.findById(postId).orElse(null);
        if (newsPost != null) {
            model.addAttribute("newsPost", newsPost);
            return "edit-news";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/delete-news/{postId}")
    public String deleteNews(@PathVariable Long postId, HttpServletRequest request) throws IOException {
        String userRole = (String) request.getSession().getAttribute("userRole");
        if ("ADMIN".equals(userRole)) {
            // Delete the news post by postId from the database
            Optional<NewsPost> newsPostOptional = newsPostRepository.findById(postId);
            NewsPost newsPost = newsPostOptional.orElse(null);
            if(newsPost != null) {
                newsPostRepository.deleteById(postId);
                fileStorageService.deleteFile(newsPost.getFileName());
                return "redirect:/";
            }
            return "redirect:/news";
        }
        return "redirect:/news";
    }

}
