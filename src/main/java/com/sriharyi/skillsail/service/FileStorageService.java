package com.sriharyi.skillsail.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    public String storeFile(MultipartFile profilePicture, String FolderName);
}
