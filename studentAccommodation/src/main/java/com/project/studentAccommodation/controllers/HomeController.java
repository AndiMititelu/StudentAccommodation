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

//    @GetMapping("/download/{fileName}")
//    public void downloadFile2(@PathVariable String fileName, HttpServletResponse response) throws IOException {
//        Resource file = fileStorageService.loadFile(fileName);
//
//        response.setContentType(file.getTy  );
//        response.setContentLength((int) file.length());
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
//
//        Files.copy(file.toPath(), response.getOutputStream());
//    }

}
