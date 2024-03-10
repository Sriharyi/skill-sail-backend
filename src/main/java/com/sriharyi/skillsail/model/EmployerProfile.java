package com.sriharyi.skillsail.model;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "employers")
public class EmployerProfile {

    @Id
    private String id;
    private String companyLogo;
    @Indexed(unique = true)
    private String companyName;
    @Indexed(unique = true)
    private String companyEmail;
    private String companyWebsite;
    private String companyDescription;
    private String companyLocation;
    private String companyIndustry;

    private boolean verified;
    private boolean deleted;
    
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;


    

}
