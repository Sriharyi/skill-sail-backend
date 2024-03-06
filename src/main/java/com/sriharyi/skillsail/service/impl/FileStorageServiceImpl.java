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
    public String storeFile(MultipartFile profilePicture,String FolderName) {
        String fileName =  StringUtils.cleanPath(profilePicture.getOriginalFilename());
        Path path = Paths.get(uploadDir + FolderName);
        String filePath = path.toFile().getAbsolutePath() + "/" + fileName;

        try {
            profilePicture.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return actualPath + FolderName + "/" + fileName;
    }
}
