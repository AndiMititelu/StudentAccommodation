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

    public void deleteFile(String fileName) throws IOException {
        Path filePath = Paths.get(storageLocation).resolve(fileName);
        Files.deleteIfExists(filePath);
    }

}
