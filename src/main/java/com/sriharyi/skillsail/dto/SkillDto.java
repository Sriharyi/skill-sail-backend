package com.sriharyi.skillsail.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkillDto {
    private String id;
    @JsonProperty("skillName")
    private String name;
    @JsonProperty("skillDescription")
    private String description;
    @JsonProperty("skillCategory")
    private String category;
    @JsonProperty("questions")
    private Set<QuestionDto> questions;
}
