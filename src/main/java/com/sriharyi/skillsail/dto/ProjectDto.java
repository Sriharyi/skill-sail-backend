package com.sriharyi.skillsail.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id")
    private String id;
    @JsonProperty("employerProfileId")
    private String employerProfileId;
    @JsonProperty("selectedFreelancerId")
    private String selectedFreelancerId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("thumbnail")
    private String thumbnail;
    @JsonProperty("fileUrl")
    private String fileUrl;
    @JsonProperty("category")
    private String category;
    @JsonProperty("skills")
    private List<String> skills;
    @JsonProperty("budget")
    private Double budget;
    @JsonProperty("status")
    private String status;
    @JsonProperty("deadline")
    private LocalDateTime deadline;
    @JsonProperty("bidDeadline")
    private LocalDateTime bidDeadline;
}
