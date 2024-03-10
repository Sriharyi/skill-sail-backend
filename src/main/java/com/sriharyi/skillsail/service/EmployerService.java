package com.sriharyi.skillsail.service;

import com.sriharyi.skillsail.dto.EmployerDto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface EmployerService {
    EmployerDto createEmployer(EmployerDto employerDto);

    List<EmployerDto> getAllEmployers();

    EmployerDto getEmployerById(String id);

    EmployerDto updateEmployer(String id, EmployerDto employerDto);

    void deleteEmployer(String id);

    String updateEmployerProfilePicture(String id, MultipartFile profilePicture);
}
