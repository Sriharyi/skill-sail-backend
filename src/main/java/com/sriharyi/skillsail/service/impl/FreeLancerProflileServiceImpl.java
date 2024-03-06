package com.sriharyi.skillsail.service.impl;

import com.sriharyi.skillsail.dto.FreelancerProfileDto;
import com.sriharyi.skillsail.exception.FreeLancerProfileNotFoundException;
import com.sriharyi.skillsail.model.FreeLancerProfile;
import com.sriharyi.skillsail.repository.FreeLancerProfileRepository;
import com.sriharyi.skillsail.service.FileStorageService;
import com.sriharyi.skillsail.service.FreeLancerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreeLancerProflileServiceImpl implements FreeLancerProfileService {

    private final FreeLancerProfileRepository freeLancerProfileRepository;

    private final FileStorageService fileStorageService;
    
    @Override
    public FreelancerProfileDto createFreeLancerProfile(FreelancerProfileDto freelancerProfileDto) {
        FreeLancerProfile freeLancerProfile = convertToEntity(freelancerProfileDto);
        FreeLancerProfile savedFreeLancerProfile = freeLancerProfileRepository.save(freeLancerProfile);
        return convertToDto(savedFreeLancerProfile);
    }

    @Override
    public List<FreelancerProfileDto> getAllFreeLancerProfiles() {
        List<FreeLancerProfile> freeLancerProfiles = freeLancerProfileRepository.findAllByDeletedFalse();
        return freeLancerProfiles.stream().map(this::convertToDto).toList();
    }

    @Override
    public FreelancerProfileDto getFreeLancerProfileById(String id) {
        FreeLancerProfile freeLancerProfile = freeLancerProfileRepository.findById(id).orElseThrow(
                () -> new FreeLancerProfileNotFoundException("FreeLancerProfile not found")
        );
        if (freeLancerProfile.isDeleted()) {
            throw new FreeLancerProfileNotFoundException("FreeLancerProfile not found");
        }
        return convertToDto(freeLancerProfile);
    }

    @Override
    public FreelancerProfileDto updateFreeLancerProfile(String id, FreelancerProfileDto freelancerProfileDto) {
        FreeLancerProfile freeLancerProfile = freeLancerProfileRepository.findById(id).orElseThrow(
                () -> new FreeLancerProfileNotFoundException("FreeLancerProfile not found")
        );
        if (freeLancerProfile.isDeleted()) {
            throw new FreeLancerProfileNotFoundException("FreeLancerProfile not found");
        }
        freelancerProfileDto.setId(id);
        FreeLancerProfile updatedFreeLancerProfile = freeLancerProfileRepository.save(convertToEntity(freelancerProfileDto));
        return convertToDto(updatedFreeLancerProfile);
    }

    @Override
    public void deleteFreeLancerProfile(String id) {
        FreeLancerProfile freeLancerProfile = freeLancerProfileRepository.findById(id).orElseThrow(
                () -> new FreeLancerProfileNotFoundException("FreeLancerProfile not found")
        );
        freeLancerProfile.setDeleted(true);
        freeLancerProfileRepository.save(freeLancerProfile);

    }

    @Override
    public String updateFreeLancerProfilePicture(String id, MultipartFile profilePicture) {
        FreeLancerProfile freeLancerProfile = freeLancerProfileRepository.findById(id).orElseThrow(
                () -> new FreeLancerProfileNotFoundException("FreeLancerProfile not found")
        );
        if (freeLancerProfile.isDeleted()) {
            throw new FreeLancerProfileNotFoundException("FreeLancerProfile not found");
        }
        String folderName = "profile-pictures";
        String imagePath = fileStorageService.storeFile(profilePicture, folderName);
        freeLancerProfile.setProfilePic(imagePath);
        freeLancerProfileRepository.save(freeLancerProfile);
        return imagePath;
    }

    protected FreeLancerProfile convertToEntity(FreelancerProfileDto freelancerProfileDto) {
        return FreeLancerProfile.builder()
                .id(freelancerProfileDto.getId())
                .displayName(freelancerProfileDto.getDisplayName())
                .userName(freelancerProfileDto.getUserName())
                .description(freelancerProfileDto.getDescription())
                .education(freelancerProfileDto.getEducation())
                .skills(freelancerProfileDto.getSkills())
                .skillsEarned(freelancerProfileDto.getSkillsEarned())
                .ratings(freelancerProfileDto.getRatings())
                .verified(freelancerProfileDto.isVerified())
                .build();
    }

    protected FreelancerProfileDto convertToDto(FreeLancerProfile freeLancerProfile) {
        return FreelancerProfileDto.builder()
                .id(freeLancerProfile.getId())
                .displayName(freeLancerProfile.getDisplayName())
                .userName(freeLancerProfile.getUserName())
                .description(freeLancerProfile.getDescription())
                .education(freeLancerProfile.getEducation())
                .skills(freeLancerProfile.getSkills())
                .skillsEarned(freeLancerProfile.getSkillsEarned())
                .ratings(freeLancerProfile.getRatings())
                .verified(freeLancerProfile.isVerified())
                .build();
    }
}
