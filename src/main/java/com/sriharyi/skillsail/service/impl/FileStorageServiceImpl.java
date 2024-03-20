package com.sriharyi.skillsail.service.impl;

import com.sriharyi.skillsail.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.actual-path}")
    private String actualPath;

    @Override
    public String storeImage(MultipartFile file, String folderName) {
        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }
        return storeFile(file, folderName);
    }
    
    @Override
    public String storePdf(MultipartFile file, String folderName) {
        if (!"application/pdf".equals(file.getContentType())) {
            throw new IllegalArgumentException("File must be a PDF");
        }
        return storeFile(file, folderName);
    }
    
    private String storeFile(MultipartFile file, String folderName) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get(uploadDir + folderName);
        String filePath = path.toFile().getAbsolutePath() + "/" + fileName;
    
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return actualPath + folderName + "/" + fileName;
    }
}
