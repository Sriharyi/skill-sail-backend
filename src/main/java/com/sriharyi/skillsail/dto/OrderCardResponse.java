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
public class OrderCardResponse {
    String id;
    String title;
    String description;
    String category;
    String skills[];
    Double bidAmount;
    String status;
    LocalDateTime deadline;
}
