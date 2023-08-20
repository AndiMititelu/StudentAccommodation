package com.project.studentAccommodation.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileStorageService {
    @Value("${storage.location}")
    private String storageLocation;

    public String storeFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        Path filePath = Paths.get(storageLocation).resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
    public Resource loadFile(String fileName) throws IOException {
        Path filePath = Paths.get(storageLocation).resolve(fileName);
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new IOException("Could not read file: " + fileName);
        }
    }

    public String getContentType(String fileName) {
        // Get the file extension.
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        // Map file extensions to MIME types.
        Map<String, String> mimeTypes = new HashMap<>();
        mimeTypes.put("pdf", "application/pdf");
        mimeTypes.put("doc", "application/msword");
        mimeTypes.put("xls", "application/vnd.ms-excel");
        mimeTypes.put("txt", "text/plain");

        // Get the MIME type for the file extension.
        String contentType = mimeTypes.get(extension);

        // If the MIME type is not found, return a default value.
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return contentType;
    }

}
