package com.sriharyi.skillsail.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sriharyi.skillsail.dto.OrderCardResponse;
import com.sriharyi.skillsail.dto.ProjectDto;
import com.sriharyi.skillsail.exception.ProjectNotFoundException;
import com.sriharyi.skillsail.model.EmployerProfile;
import com.sriharyi.skillsail.model.FreeLancerProfile;
import com.sriharyi.skillsail.model.Project;
import com.sriharyi.skillsail.model.enums.ProjectStatus;
import com.sriharyi.skillsail.repository.ProjectRepository;
import com.sriharyi.skillsail.service.FileStorageService;
import com.sriharyi.skillsail.service.ProjectService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final FileStorageService fileStorageService;

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        Project project = mapToProject(projectDto);
        project = projectRepository.save(project);
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
        if (project.isDeleted()) {
            throw new ProjectNotFoundException("Project not found ");
        }
        return mapToProjectDto(project);
    }

    @Override
    public ProjectDto updateProject(String id, ProjectDto projectDto) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("Project not found "));
        if (project.isDeleted()) {
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
        if (project.isDeleted()) {
            throw new ProjectNotFoundException("Project not found ");
        }
        project.setDeleted(true);
        projectRepository.save(project);
    }

    protected ProjectDto mapToProjectDto(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .employerProfileId(project.getEmployerProfileId().getId())
                .selectedFreelancerId(project.getSelectedFreelancerId().getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .category(project.getCategory())
                .skills(project.getSkills())
                .budget(project.getBudget())
                .status(project.getStatus().name())
                .deadline(project.getDeadline())
                .bidDeadline(project.getBidDeadline())
                .build();
    }

    protected Project mapToProject(ProjectDto projectDto) {
        ProjectStatus status = projectDto.getStatus() == null ? ProjectStatus.OPEN
                : ProjectStatus.valueOf(projectDto.getStatus());
        EmployerProfile employerProfile = EmployerProfile.builder().id(projectDto.getEmployerProfileId())
                .build();
        FreeLancerProfile freeLancerProfile = FreeLancerProfile.builder().id(projectDto.getSelectedFreelancerId())
                .build();
        return Project.builder()
                .id(projectDto.getId())
                .employerProfileId(employerProfile)
                .selectedFreelancerId(freeLancerProfile)
                .title(projectDto.getTitle())
                .description(projectDto.getDescription())
                .category(projectDto.getCategory())
                .skills(projectDto.getSkills())
                .budget(projectDto.getBudget())
                .status(status)
                .deadline(projectDto.getDeadline())
                .bidDeadline(projectDto.getBidDeadline())
                .build();
    }

    @Override
    public Page<ProjectDto> getProjectsByPage(Pageable pageable) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities();

        boolean isAdmin = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return projectRepository.findAll(pageable).map(this::mapToProjectDto);
        } else {
            Page<Project> projects = projectRepository.findAllByDeletedFalseAndStatus(pageable,
                    ProjectStatus.OPEN.name());
            return projects.map(this::mapToProjectDto);
        }
    }   

    @Override
    public List<ProjectDto> getProjectsByEmployerId(String employerId) {
        List<Project> projects =  projectRepository.findAllByEmployerProfileId_IdAndDeletedFalse(employerId);
        return projects.stream()
                .map(this::mapToProjectDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderCardResponse> getProjectsByFreelancerId(String freelancerId) {
        List<Project> projects = projectRepository.findAllBySelectedFreelancerId_IdAndDeletedFalse(freelancerId);
        return convertToOrderCardResponse(projects);
    }

    private List<OrderCardResponse> convertToOrderCardResponse(List<Project> projects) {
        return projects.stream().map(
            (project) -> OrderCardResponse.builder()
                    .id(project.getId())
                    .title(project.getTitle())
                    .description(project.getDescription())
                    .category(project.getCategory())
                    .skills(project.getSkills().toArray(new String[0]))
                    .bidAmount(project.getBidAmount())
                    .status(project.getStatus().name())
                    .deadline(project.getDeadline())
                    .build()    
        ).collect(Collectors.toList());
    }

    @Override
    public ProjectDto addFileToProject(String id, MultipartFile file) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("Project not found "));
        if (project.isDeleted()) {
            throw new ProjectNotFoundException("Project not found ");
        }
        String folderName = "files/project-files";
        String fileUrl = fileStorageService.storePdf(file, folderName);
        project.setFileUrl(fileUrl);
        project = projectRepository.save(project);
        return mapToProjectDto(project);
    }

}
