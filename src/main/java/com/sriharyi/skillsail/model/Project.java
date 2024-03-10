package com.sriharyi.skillsail.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.sriharyi.skillsail.model.enums.ProjectStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "projects")
public class Project {

    private String id;

    @DocumentReference(collection = "employers", lazy = true)
    private EmployerProfile employerProfileId;

    @DocumentReference(collection = "freelancers", lazy = true)
    private FreeLancerProfile selectedFreelancerId;

    private String title;

    private String description;

    private String category;

    private List<String> skills;

    private Double budget;

    @Field(targetType = FieldType.STRING)
    private ProjectStatus status;

    private Double bidAmount;

    private LocalDateTime deadline;

    private LocalDateTime bidDeadline;

    @CreatedDate
    private LocalDateTime createdDate;
    
    @LastModifiedDate
    private LocalDateTime updatedDate;

    private boolean deleted;
}
