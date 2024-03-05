package com.sriharyi.skillsail.service.impl;

import com.sriharyi.skillsail.dto.ProjectDto;
import com.sriharyi.skillsail.exception.ProjectNotFoundException;
import com.sriharyi.skillsail.model.Project;
import com.sriharyi.skillsail.model.enums.ProjectStatus;
import com.sriharyi.skillsail.repository.ProjectRepository;
import com.sriharyi.skillsail.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private  final ProjectRepository projectRepository;
    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        Project project =  mapToProject(projectDto);
        project =  projectRepository.save(project);
        return mapToProjectDto(project);

    }

    @Override
    public List<ProjectDto> getAllProjects() {
        List<Project> projects = projectRepository.findAllByDeletedFalse();
        return projects.stream()
                .map(this::mapToProjectDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDto getProjectById(String id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("Project not found "));
        if(project.isDeleted()){
            throw new ProjectNotFoundException("Project not found ");
        }
        return mapToProjectDto(project);
    }

    @Override
    public ProjectDto updateProject(String id, ProjectDto projectDto) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("Project not found "));
        if(project.isDeleted()){
            throw new ProjectNotFoundException("Project not found ");
        }
        project = mapToProject(projectDto);
        project.setId(id);
        project = projectRepository.save(project);
        return mapToProjectDto(project);
    }

    @Override
    public void deleteProject(String id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("Project not found "));
        if(project.isDeleted()){
            throw new ProjectNotFoundException("Project not found ");
        }
        project.setDeleted(true);
        projectRepository.save(project);
    }

    protected ProjectDto mapToProjectDto(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .employerProfile(project.getEmployerProfile())
                .selectedFreelancerId(project.getSelectedFreelancerId())
                .title(project.getTitle())
                .description(project.getDescription())
                .skills(project.getSkills())
                .budget(project.getBudget())
                .status(project.getStatus().name())
                .deadline(project.getDeadline())
                .build();
    }
    
    protected Project mapToProject(ProjectDto projectDto) {
        ProjectStatus status = projectDto.getStatus() == null ? ProjectStatus.OPEN : ProjectStatus.valueOf(projectDto.getStatus());
        return Project.builder()
                .id(projectDto.getId())
                .employerProfile(projectDto.getEmployerProfile())
                .selectedFreelancerId(projectDto.getSelectedFreelancerId())
                .title(projectDto.getTitle())
                .description(projectDto.getDescription())
                .skills(projectDto.getSkills())
                .budget(projectDto.getBudget())
                .status(status)
                .deadline(projectDto.getDeadline())
                .build();
    }
}
