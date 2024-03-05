package com.sriharyi.skillsail.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

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
    private String description;
    
    @DBRef()
    private Set<Question> questions;
    private boolean deleted;
}