package com.sriharyi.skillsail.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sriharyi.skillsail.model.Rating;
import com.sriharyi.skillsail.model.Skill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FreelancerProfileDto {
    private String id;
    private String profilePic;
    private String displayName;
    private String userName;
    private String description;
    private List<Education> educations;
    private List<String> skills;
    private List<String> skillsEarned; // skills that the freelancer has earned from assessments
    private List<Rating> ratings;
    private boolean verified;
}

