package com.sriharyi.skillsail.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sriharyi.skillsail.dto.EmployerDto;
import com.sriharyi.skillsail.exception.EmployerNotFoundException;
import com.sriharyi.skillsail.model.EmployerProfile;
import com.sriharyi.skillsail.repository.EmployerRepository;
import com.sriharyi.skillsail.service.EmployerService;
import com.sriharyi.skillsail.service.FileStorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployerServiceImpl implements EmployerService {

    private final EmployerRepository employerRepository;

    private final FileStorageService fileStorageService;

    @Override
    public EmployerDto createEmployer(EmployerDto employerDto) {
        EmployerProfile employerProfile = mapToEmployerProfile(employerDto);
        employerProfile = employerRepository.save(employerProfile);
        return mapToEmployerDto(employerProfile);
    }

    @Override
    public List<EmployerDto> getAllEmployers() {
        List<EmployerProfile> employerProfiles = employerRepository.findAllByDeletedFalse();
        return employerProfiles.stream()
                .map(this::mapToEmployerDto)
                .toList();
    }

    @Override
    public EmployerDto getEmployerById(String id) {
        EmployerProfile employerProfile = employerRepository.findById(id)
                .orElseThrow(() -> new EmployerNotFoundException("Employer not found"));
        if (employerProfile.isDeleted()) {
            throw new EmployerNotFoundException("Employer not found");
        }
        return mapToEmployerDto(employerProfile);
    }

    @Override
    public EmployerDto updateEmployer(String id, EmployerDto employerDto) {
        EmployerProfile employerProfile = employerRepository.findById(id)
                .orElseThrow(() -> new EmployerNotFoundException("Employer not found"));
        if (employerProfile.isDeleted()) {
            throw new EmployerNotFoundException("Employer not found");
        }

        if (employerDto.getCompanyName() != null)
            employerProfile.setCompanyName(employerDto.getCompanyName());
        if (employerDto.getCompanyEmail() != null)
            employerProfile.setCompanyEmail(employerDto.getCompanyEmail());
        if (employerDto.getCompanyWebsite() != null)
            employerProfile.setCompanyWebsite(employerDto.getCompanyWebsite());
        if (employerDto.getCompanyDescription() != null)
            employerProfile.setCompanyDescription(employerDto.getCompanyDescription());
        if (employerDto.getCompanyLogo() != null)
            employerProfile.setCompanyLogo(employerDto.getCompanyLogo());
        if (employerDto.getCompanyLocation() != null)
            employerProfile.setCompanyLocation(employerDto.getCompanyLocation());
        if (employerDto.getCompanyIndustry() != null)
            employerProfile.setCompanyIndustry(employerDto.getCompanyIndustry());
        if (employerDto.isVerified())
            employerProfile.setVerified(true);
        System.out.println(employerProfile);

        EmployerProfile updatedEmployerProfile = employerRepository.save(employerProfile);
        return mapToEmployerDto(updatedEmployerProfile);
    }

    @Override
    public void deleteEmployer(String id) {
        EmployerProfile employerProfile = employerRepository.findById(id)
                .orElseThrow(() -> new EmployerNotFoundException("Employer not found"));
        employerProfile.setDeleted(true);
        employerRepository.save(employerProfile);
    }

    @Override
    public String updateEmployerProfilePicture(String id, MultipartFile profilePicture) {
        EmployerProfile employerProfile = employerRepository.findById(id)
                .orElseThrow(() -> new EmployerNotFoundException("Employer not found"));
        if (employerProfile.isDeleted()) {
            throw new EmployerNotFoundException("Employer not found");
        }
        String folderName = "company-logos";
        String imagePath = fileStorageService.storeFile(profilePicture, folderName);
        employerProfile.setCompanyLogo(imagePath);
        employerRepository.save(employerProfile);
        return imagePath;
    }

    protected EmployerDto mapToEmployerDto(EmployerProfile employerProfile) {
        return EmployerDto.builder()
                .id(employerProfile.getId())
                .companyName(employerProfile.getCompanyName())
                .companyEmail(employerProfile.getCompanyEmail())
                .companyWebsite(employerProfile.getCompanyWebsite())
                .companyDescription(employerProfile.getCompanyDescription())
                .companyLogo(employerProfile.getCompanyLogo())
                .companyLocation(employerProfile.getCompanyLocation())
                .companyIndustry(employerProfile.getCompanyIndustry())
                .verified(employerProfile.isVerified())
                .build();
    }

    protected EmployerProfile mapToEmployerProfile(EmployerDto employerDto) {
        return EmployerProfile.builder()
                .id(employerDto.getId())
                .companyName(employerDto.getCompanyName())
                .companyEmail(employerDto.getCompanyEmail())
                .companyWebsite(employerDto.getCompanyWebsite())
                .companyDescription(employerDto.getCompanyDescription())
                .companyLogo(employerDto.getCompanyLogo())
                .companyLocation(employerDto.getCompanyLocation())
                .companyIndustry(employerDto.getCompanyIndustry())
                .verified(employerDto.isVerified())
                .build();
    }

}
