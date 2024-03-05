package com.sriharyi.skillsail.service.impl;

import com.sriharyi.skillsail.dto.EmployerDto;
import com.sriharyi.skillsail.exception.EmployerNotFoundException;
import com.sriharyi.skillsail.model.EmployerProfile;
import com.sriharyi.skillsail.repository.EmployerRepository;
import com.sriharyi.skillsail.service.EmployerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployerServiceImpl implements EmployerService {

    private final EmployerRepository employerRepository;
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
                .orElseThrow(() -> new EmployerNotFoundException("Employer not found" ));
    }

    @Override
    public EmployerDto updateEmployer(String id, EmployerDto employerDto) {
        EmployerProfile employerProfile = employerRepository.findById(id)
                .orElseThrow(() -> new EmployerNotFoundException("Employer not found" ));
        if(employerProfile.isDeleted()){
            throw new EmployerNotFoundException("Employer not found");
        }
        employerDto.setId(id);
        EmployerProfile updatedEmployerProfile = employerRepository.save(mapToEmployerProfile(employerDto));
        return mapToEmployerDto(updatedEmployerProfile);
    }

    @Override
    public void deleteEmployer(String id) {
        EmployerProfile employerProfile = employerRepository.findById(id)
                .orElseThrow(() -> new EmployerNotFoundException("Employer not found" ));
        employerProfile.setDeleted(true);
        employerRepository.save(employerProfile);
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
                .verified(employerProfile.isVerified())
                .industry(employerProfile.getIndustry())
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
                .verified(employerDto.isVerified())
                .industry(employerDto.getIndustry())
                .build();
    }
}
