package com.sriharyi.skillsail.service.impl;

import com.sriharyi.skillsail.dto.FreelancerProfileDto;
import com.sriharyi.skillsail.exception.FreeLancerProfileNotFoundException;
import com.sriharyi.skillsail.model.FreeLancerProfile;
import com.sriharyi.skillsail.repository.FreeLancerProfileRepository;
import com.sriharyi.skillsail.service.FreeLancerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreeLancerProflileServiceImpl implements FreeLancerProfileService {

    private final FreeLancerProfileRepository freeLancerProfileRepository;

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

    protected FreeLancerProfile convertToEntity(FreelancerProfileDto freelancerProfileDto) {
        return FreeLancerProfile.builder()
                .id(freelancerProfileDto.getId())
                .displayName(freelancerProfileDto.getDisplayName())
                .userName(freelancerProfileDto.getUserName())
                .description(freelancerProfileDto.getDescription())
                .education(freelancerProfileDto.getEducation())
                .build();
    }

    protected FreelancerProfileDto convertToDto(FreeLancerProfile freeLancerProfile) {
        return FreelancerProfileDto.builder()
                .id(freeLancerProfile.getId())
                .displayName(freeLancerProfile.getDisplayName())
                .userName(freeLancerProfile.getUserName())
                .description(freeLancerProfile.getDescription())
                .education(freeLancerProfile.getEducation())
                .build();
    }
}
