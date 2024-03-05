package com.sriharyi.skillsail.model;

import com.sriharyi.skillsail.model.enums.ProjectStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.List;

public class Project {

    private String id;
    @DBRef
    private EmployerProfile employerProfile;
    @DBRef
    private FreeLancerProfile selectedFreelancerId;
    private String title;
    private String description;
    private List<Skill> skills;
    private Double budget;
    @Field(targetType = FieldType.STRING)
    private ProjectStatus status;
    private Date deadline;

    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date updatedDate;

    private boolean deleted;
}
