package com.sriharyi.skillsail.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sriharyi.skillsail.dto.Education;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "freelancers")
public class FreeLancerProfile {
    @Id
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
    private boolean deleted;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime updatedDate;
}
