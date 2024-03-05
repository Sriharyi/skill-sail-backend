package com.sriharyi.skillsail.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployerDto {
    private String id;
    private String companyLogo;
    private String companyName;
    private String companyEmail;
    private String companyWebsite;
    private String companyDescription;
    private String companyLocation;
    private String industry;
    private boolean verified;

}
