package com.sriharyi.skillsail.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BidDto {
    private String id;
    private String projectId;
    private String freelancerId;
    private String proposal;
    private double bidAmount;
    private String bidStatus;
}
