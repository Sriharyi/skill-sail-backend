package com.sriharyi.skillsail.service;

import com.sriharyi.skillsail.dto.FreelancerProfileDto;

import java.util.List;

public interface FreeLancerProfileService {


    FreelancerProfileDto createFreeLancerProfile(FreelancerProfileDto freelancerProfileDto);

    List<FreelancerProfileDto> getAllFreeLancerProfiles();

    FreelancerProfileDto getFreeLancerProfileById(String id);

    FreelancerProfileDto updateFreeLancerProfile(String id, FreelancerProfileDto freelancerProfileDto);

    void deleteFreeLancerProfile(String id);
}
