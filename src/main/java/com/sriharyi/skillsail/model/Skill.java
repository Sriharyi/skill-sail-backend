package com.sriharyi.skillsail.model;


import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "skills")
public class Skill {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    @Indexed(unique = true)
    private String category;

    private String description;
    
    @DocumentReference(collection = "questions",lazy = true)
    private Set<Question> questions;
    
    private boolean deleted;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date updatedDate;
}