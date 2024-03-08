package com.sriharyi.skillsail.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sriharyi.skillsail.model.EmployerProfile;
import com.sriharyi.skillsail.model.FreeLancerProfile;
import com.sriharyi.skillsail.model.Skill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDto {
    private String id;
    private EmployerProfile employerProfileId;
    private FreeLancerProfile selectedFreelancerId;
    private String title;
    private String description;
    private String category;
    private List<String> skills;
    private Double budget;
    private String status;
    private LocalDateTime deadline;
    private LocalDateTime bidDeadline;
}
