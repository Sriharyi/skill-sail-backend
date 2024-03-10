package com.sriharyi.skillsail.model;

import com.sriharyi.skillsail.model.enums.TestStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "assessments")
public class Assessment {
    @Id
    private String id;

    @DocumentReference(collection = "freelancers",lazy = true)
    private FreeLancerProfile freelancerProfile;

    @DocumentReference(collection = "skills",lazy = true)
    private Skill skill;

    private Integer score;

    private String feedback;

    private TestStatus status;

    private boolean deleted;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

}
