package com.sriharyi.skillsail.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sriharyi.skillsail.model.Rating;
import com.sriharyi.skillsail.model.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    private List<Education> education;
    private List<String> skills;
    private List<Skill> skillsEarned; // skills that the freelancer has earned from assessments
    private List<Rating> ratings;
    private boolean verified;

}

