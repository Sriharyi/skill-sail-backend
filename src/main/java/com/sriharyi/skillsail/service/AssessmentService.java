package com.sriharyi.skillsail.service;

import com.sriharyi.skillsail.dto.AssessmentDto;

import java.util.List;

public interface AssessmentService {
    AssessmentDto createAssessment(AssessmentDto assessmentDto);

    List<AssessmentDto> getAllAssessments();

    AssessmentDto getAssessment(String id);

    AssessmentDto updateAssessment(String id,AssessmentDto assessmentDto);

    AssessmentDto deleteAssessment(String id);
}
