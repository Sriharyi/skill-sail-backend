package com.sriharyi.skillsail.dto;

import java.time.LocalDateTime;

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
public class FreelancerBidResponse {
    private String id;
    private String projectId;
    private String projectName;
    private String companyName;
    private LocalDateTime BidDeadline;
    private Double bidAmount;
    private String status;
}
