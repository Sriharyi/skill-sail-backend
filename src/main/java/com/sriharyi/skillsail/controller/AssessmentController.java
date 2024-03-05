package com.sriharyi.skillsail.controller;

import com.sriharyi.skillsail.dto.AssessmentDto;
import com.sriharyi.skillsail.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    @PostMapping
    private ResponseEntity<AssessmentDto> createAssessment(@RequestBody AssessmentDto assessmentDto) {
        AssessmentDto createdAssessment = assessmentService.createAssessment(assessmentDto);
        return new ResponseEntity<>(createdAssessment, HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<List<AssessmentDto>> getAllAssessments() {
        List<AssessmentDto> assessments = assessmentService.getAllAssessments();
        return new ResponseEntity<>(assessments, HttpStatus.OK);
    }



    @GetMapping("/{id}")
    private ResponseEntity<AssessmentDto> getAssessment(@PathVariable String id) {
        AssessmentDto assessment = assessmentService.getAssessment(id);
        return new ResponseEntity<>(assessment, HttpStatus.OK);
    }



    @PutMapping("/{id}")
    private ResponseEntity<AssessmentDto> updateAssessment(@PathVariable String id,@RequestBody AssessmentDto assessmentDto) {
        AssessmentDto updatedAssessment = assessmentService.updateAssessment(id,assessmentDto);
        return new ResponseEntity<>(updatedAssessment, HttpStatus.OK);
    }

    @DeleteMapping
    private ResponseEntity<AssessmentDto> deleteAssessment(@RequestParam String id) {
        AssessmentDto deletedAssessment = assessmentService.deleteAssessment(id);
        return new ResponseEntity<>(deletedAssessment, HttpStatus.OK);
    }
//
//    @GetMapping("/freelancer")
//    private ResponseEntity<AssessmentDto> getAssessmentByFreelancer(@RequestParam String freelancerProfileId) {
//        AssessmentDto assessment = assessmentService.getAssessmentByFreelancer(freelancerProfileId);
//        return new ResponseEntity<>(assessment, HttpStatus.OK);
//    }
//
//    @GetMapping("/skill")
//    private ResponseEntity<AssessmentDto> getAssessmentBySkill(@RequestParam String skillId) {
//        AssessmentDto assessment = assessmentService.getAssessmentBySkill(skillId);
//        return new ResponseEntity<>(assessment, HttpStatus.OK);
//    }
//
//    @GetMapping("/status")
//    private ResponseEntity<AssessmentDto> getAssessmentByStatus(@RequestParam String status) {
//        AssessmentDto assessment = assessmentService.getAssessmentByStatus(status);
//        return new ResponseEntity<>(assessment, HttpStatus.OK);
//    }
//
//    @GetMapping("/all")
//    private ResponseEntity<AssessmentDto> getAllAssessments() {
//        AssessmentDto assessment = assessmentService.getAllAssessments();
//        return new ResponseEntity<>(assessment, HttpStatus.OK);
//    }
//
//
}
