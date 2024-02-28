package com.sriharyi.skillsail.controller;


import com.sriharyi.skillsail.dto.FreelancerProfileDto;
import com.sriharyi.skillsail.service.FreeLancerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/freelancer")
public class FreeLancerProfileController {

    private final FreeLancerProfileService freeLancerProfileService;

    @PostMapping
    public ResponseEntity<FreelancerProfileDto> createFreeLancerProfile(@RequestBody FreelancerProfileDto freelancerProfileDto) {
        FreelancerProfileDto createdFreeLancerProfile = freeLancerProfileService.createFreeLancerProfile(freelancerProfileDto);
        return new ResponseEntity<>(createdFreeLancerProfile, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FreelancerProfileDto>> getAllFreeLancerProfiles() {
        List<FreelancerProfileDto> freeLancerProfiles = freeLancerProfileService.getAllFreeLancerProfiles();
        return new ResponseEntity<>(freeLancerProfiles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FreelancerProfileDto> getFreeLancerProfileById(@PathVariable String id) {
        FreelancerProfileDto freeLancerProfile = freeLancerProfileService.getFreeLancerProfileById(id);
        return new ResponseEntity<>(freeLancerProfile, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FreelancerProfileDto> updateFreeLancerProfile(@PathVariable String id, @RequestBody FreelancerProfileDto freelancerProfileDto) {
        FreelancerProfileDto updatedFreeLancerProfile = freeLancerProfileService.updateFreeLancerProfile(id, freelancerProfileDto);
        return new ResponseEntity<>(updatedFreeLancerProfile, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFreeLancerProfile(@PathVariable String id) {
        freeLancerProfileService.deleteFreeLancerProfile(id);
        return new ResponseEntity<>("Successfully Deleted",HttpStatus.OK);
    }
}
