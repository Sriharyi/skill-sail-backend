package com.sriharyi.skillsail.model;

import com.sriharyi.skillsail.model.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "projects")
public class Project {

    private String id;
    @DocumentReference(collection = "employers",lazy = true)
    private EmployerProfile employerProfile;
    @DocumentReference(collection = "freelancers",lazy = true)
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
