package com.sriharyi.skillsail.model;

import com.sriharyi.skillsail.model.enums.TestStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "assessments")
public class Assessment {
    @Id
    private String id;

    @DBRef
    private FreeLancerProfile freelancerProfile;

    @DBRef
    private Skill skill;

    private Integer score;

    private String feedback;

    private TestStatus status;

    private boolean deleted;
}
