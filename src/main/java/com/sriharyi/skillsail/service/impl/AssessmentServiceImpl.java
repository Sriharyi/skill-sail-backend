package com.sriharyi.skillsail.service.impl;

import com.sriharyi.skillsail.dto.AssessmentDto;
import com.sriharyi.skillsail.dto.CanTakeTest;
import com.sriharyi.skillsail.exception.AssesmentNotFoundException;
import com.sriharyi.skillsail.model.Assessment;
import com.sriharyi.skillsail.model.FreeLancerProfile;
import com.sriharyi.skillsail.model.Skill;
import com.sriharyi.skillsail.model.enums.TestStatus;
import com.sriharyi.skillsail.repository.AssessmentRepository;
import com.sriharyi.skillsail.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentRepository assessmentRepository;

    @Override
    public AssessmentDto createAssessment(AssessmentDto assessmentDto) {
        Assessment assessment = mapToAssessment(assessmentDto);
        Assessment createdAssessment = assessmentRepository.save(assessment);
        return mapToAssessmentDto(createdAssessment);
    }

    @Override
    public List<AssessmentDto> getAllAssessments() {
        List<Assessment> assessments = assessmentRepository.findAll();
        return assessments.stream().map(this::mapToAssessmentDto).toList();
    }

    @Override
    public AssessmentDto getAssessment(String id) {
        Assessment assessment = assessmentRepository.findById(id).orElseThrow(
                () -> new AssesmentNotFoundException("Assessment not found ")
        );
        if (assessment.isDeleted()) {
            throw new AssesmentNotFoundException("Assessment not found ");
        }
        return mapToAssessmentDto(assessment);
    }

    @Override
    public AssessmentDto updateAssessment(String id, AssessmentDto assessmentDto) {
        Assessment assessment = assessmentRepository.findById(id).orElseThrow(
                () -> new AssesmentNotFoundException("Assessment not found ")
        );
        if (assessment.isDeleted()) {
            throw new AssesmentNotFoundException("Assessment not found ");
        }
        assessmentDto.setId(id);
        Assessment updatedAssessment = assessmentRepository.save(mapToAssessment(assessmentDto));
        return mapToAssessmentDto(updatedAssessment);
    }

    @Override
    public AssessmentDto deleteAssessment(String id) {
        Assessment assessment = assessmentRepository.findById(id).orElseThrow(
                () -> new AssesmentNotFoundException("Assessment not found "));
        assessment.setDeleted(true);
        assessmentRepository.save(assessment);
        return mapToAssessmentDto(assessment);
    }

    @Override
    public CanTakeTest canTakeAssessment(String freelancerProfileId, String skillId) {
            List<Assessment> assessments = assessmentRepository.findByFreelancerProfile_Id(freelancerProfileId);
            log.info("Assessments for freelancer: {}", assessments);

//        Optional<Assessment> latestAssessment = assessmentRepository.findFirstByFreelancerProfileAndSkillOrderByUpdatedDateDesc(freelancerProfileId, skillId);
//        if (latestAssessment.isPresent()) {
//            return CanTakeTest.builder().canTake(false).build();
//        }
        return CanTakeTest.builder().canTake(true).build();
    }

    protected Assessment mapToAssessment(AssessmentDto assessmentDto) {
        TestStatus status = assessmentDto.getStatus() == null ? TestStatus.PROGRESS : TestStatus.valueOf(assessmentDto.getStatus());
        FreeLancerProfile freelancerProfile = FreeLancerProfile.builder().id(assessmentDto.getFreelancerId()).build();
        Skill skill = Skill.builder().id(assessmentDto.getSkillId()).build();
        return Assessment.builder()
                .id(assessmentDto.getId())
                .freelancerProfile(freelancerProfile)
                .skill(skill)
                .status(status)
                .build();
    }

    protected AssessmentDto mapToAssessmentDto(Assessment assessment) {
        return AssessmentDto.builder()
                .id(assessment.getId())
                .freelancerId(assessment.getFreelancerProfile().getId())
                .skillId(assessment.getSkill().getId())
                .status(assessment.getStatus().name())
                .build();
    }
}
