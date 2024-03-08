package com.sriharyi.skillsail.service;

import com.sriharyi.skillsail.dto.ProjectDto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

    ProjectDto createProject(ProjectDto projectDto);

    public List<ProjectDto> getAllProjects();

    ProjectDto getProjectById(String id);

    ProjectDto updateProject(String id, ProjectDto projectDto);

    void deleteProject(String id);

    Page<ProjectDto> getProjectsByPage(Pageable pageable);
}
