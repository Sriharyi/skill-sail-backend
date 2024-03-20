package com.sriharyi.skillsail.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sriharyi.skillsail.dto.EarnedSkillsResponse;
import com.sriharyi.skillsail.dto.FreelancerProfileDto;
import com.sriharyi.skillsail.exception.FreeLancerProfileNotFoundException;
import com.sriharyi.skillsail.exception.SkillNotFoundException;
import com.sriharyi.skillsail.model.FreeLancerProfile;
import com.sriharyi.skillsail.model.Skill;
import com.sriharyi.skillsail.repository.FreeLancerProfileRepository;
import com.sriharyi.skillsail.repository.SkillRepository;
import com.sriharyi.skillsail.service.FileStorageService;
import com.sriharyi.skillsail.service.FreeLancerProfileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FreeLancerProflileServiceImpl implements FreeLancerProfileService {

    private final FreeLancerProfileRepository freeLancerProfileRepository;

    private final SkillRepository skillRepository;

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
        if(freelancerProfileDto.getDisplayName() != null)
            freeLancerProfile.setDisplayName(freelancerProfileDto.getDisplayName());
        if(freelancerProfileDto.getUserName() != null)
            freeLancerProfile.setUserName(freelancerProfileDto.getUserName());
        if(freelancerProfileDto.getDescription() != null)
            freeLancerProfile.setDescription(freelancerProfileDto.getDescription());
        if(freelancerProfileDto.getEducations() != null)
            freeLancerProfile.setEducations(freelancerProfileDto.getEducations());
        if(freelancerProfileDto.getSkills() != null)
            freeLancerProfile.setSkills(freelancerProfileDto.getSkills());
        if(freelancerProfileDto.getRatings() != null)
            freeLancerProfile.setRatings(freelancerProfileDto.getRatings());
        if(freelancerProfileDto.isVerified())
            freeLancerProfile.setVerified(freelancerProfileDto.isVerified());
        FreeLancerProfile updatedFreeLancerProfile = freeLancerProfileRepository.save(freeLancerProfile);
    
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
        String folderName = "images/profile-pictures";
        String imagePath = fileStorageService.storeImage(profilePicture, folderName);
        freeLancerProfile.setProfilePic(imagePath);
        freeLancerProfileRepository.save(freeLancerProfile);
        return imagePath;
    }

    @Override
    public EarnedSkillsResponse getSkillsByFreeLancerProfileId(String id) {
        FreeLancerProfile freeLancerProfile = freeLancerProfileRepository.findById(id).orElseThrow(
                () -> new FreeLancerProfileNotFoundException("FreeLancerProfile not found")
        );
        if (freeLancerProfile.isDeleted()) {
            throw new FreeLancerProfileNotFoundException("FreeLancerProfile not found");
        }


        List<Skill> skills  = freeLancerProfile.getSkillsEarned();
        List<Skill> actualSkills = new ArrayList<>();

        if(skills == null) return EarnedSkillsResponse.builder().skills(new ArrayList<>()).build();
        for (Skill skill : skills) {
            Skill actualSkill = skillRepository.findById(skill.getId()).orElseThrow(
                    () -> new SkillNotFoundException("Skill not found")
            );
            actualSkills.add(actualSkill);
        }

        

        EarnedSkillsResponse earnedSkillsResponse = EarnedSkillsResponse.builder()
                .skills(actualSkills.stream().map( skill -> skill.getName()).toList())
                .build();
        return earnedSkillsResponse;
    }

    protected FreeLancerProfile convertToEntity(FreelancerProfileDto freelancerProfileDto) {
        return FreeLancerProfile.builder()
                .id(freelancerProfileDto.getId())
                .displayName(freelancerProfileDto.getDisplayName())
                .profilePic(freelancerProfileDto.getProfilePic())
                .userName(freelancerProfileDto.getUserName())
                .description(freelancerProfileDto.getDescription())
                .educations(freelancerProfileDto.getEducations())
                .skills(freelancerProfileDto.getSkills())
                .ratings(freelancerProfileDto.getRatings())
                .verified(freelancerProfileDto.isVerified())
                .build();
    }

    protected FreelancerProfileDto convertToDto(FreeLancerProfile freeLancerProfile) {
        return FreelancerProfileDto.builder()
                .id(freeLancerProfile.getId())
                .displayName(freeLancerProfile.getDisplayName())
                .profilePic(freeLancerProfile.getProfilePic())
                .userName(freeLancerProfile.getUserName())
                .description(freeLancerProfile.getDescription())
                .educations(freeLancerProfile.getEducations())
                .skills(freeLancerProfile.getSkills())
                .ratings(freeLancerProfile.getRatings())
                .verified(freeLancerProfile.isVerified())
                .build();
    }
}
