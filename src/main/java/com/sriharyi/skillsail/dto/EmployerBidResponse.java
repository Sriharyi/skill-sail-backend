package com.sriharyi.skillsail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployerBidResponse {
    private String id;
    private String freelancerName;
    private Integer freelancerRating;
    private String proposal;
    private double bidAmount;
}
