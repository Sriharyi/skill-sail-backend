package com.sriharyi.skillsail.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    public String storeImage(MultipartFile file, String folderName);
    public String storePdf(MultipartFile file, String folderName);
}
