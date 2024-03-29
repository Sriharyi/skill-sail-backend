package com.sriharyi.skillsail.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sriharyi.skillsail.dto.OrderCardResponse;
import com.sriharyi.skillsail.dto.ProjectDto;
import com.sriharyi.skillsail.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {
        ProjectDto createdProject = projectService.createProject(projectDto);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> projects = projectService.getAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable String id) {
        ProjectDto project = projectService.getProjectById(id);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable String id, @RequestBody ProjectDto projectDto) {
        ProjectDto updatedProject = projectService.updateProject(id, projectDto);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProjectDto>> getProjectsByPage(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<ProjectDto> projects = projectService.getProjectsByPage(pageable);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/employer/{employerId}")
    public ResponseEntity<List<ProjectDto>> getProjectsByEmployerId(@PathVariable String employerId) {
        List<ProjectDto> projects = projectService.getProjectsByEmployerId(employerId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    //get page of projects by employer id
    @GetMapping("/employer/{employerId}/page")
    public ResponseEntity<Page<ProjectDto>> getProjectsByEmployerId(@PathVariable String employerId, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<ProjectDto> projects = projectService.getProjectsByEmployerId(employerId, pageable);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    //perform search by skill
    @GetMapping("/search")
    public ResponseEntity<Page<ProjectDto>> searchProjectsBySkill(@RequestParam String searchText, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<ProjectDto> projects = projectService.searchProjectsBySkill(searchText, pageable);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    //get page of projects by skill and employer id
    @GetMapping("/employer/{employerId}/search")
    public ResponseEntity<Page<ProjectDto>> searchProjectsBySkillAndEmployerId(@RequestParam String searchText, @PathVariable String employerId, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<ProjectDto> projects = projectService.searchProjectsBySkillAndEmployerId(searchText, employerId, pageable);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/freelancer/{freelancerId}")
    public ResponseEntity<List<OrderCardResponse>> getProjectsByFreelancerId(@PathVariable String freelancerId) {
        List<OrderCardResponse> projects = projectService.getProjectsByFreelancerId(freelancerId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PutMapping("/{id}/file")
    public ResponseEntity<ProjectDto> addFileToProject(@RequestParam MultipartFile file, @PathVariable String id) {
        ProjectDto project = projectService.addFileToProject(id, file);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PutMapping("/{id}/thumbnail")
    public ResponseEntity<ProjectDto> addThumbnailToProject(@RequestParam MultipartFile thumbnail, @PathVariable String id) {
        ProjectDto project = projectService.addThumbnailToProject(id, thumbnail);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

}
