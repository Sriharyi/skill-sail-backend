package com.sriharyi.skillsail.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sriharyi.skillsail.model.EmployerProfile;
import com.sriharyi.skillsail.model.FreeLancerProfile;
import com.sriharyi.skillsail.model.Skill;
import com.sriharyi.skillsail.model.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDto {
    private String id;
    private EmployerProfile employerProfile;
    private FreeLancerProfile selectedFreelancerId;
    private String title;
    private String description;
    private List<Skill> skills;
    private Double budget;
    private String status;
    private LocalDateTime deadline;
}
