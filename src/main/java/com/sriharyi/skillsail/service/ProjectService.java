package com.sriharyi.skillsail.service;

import com.sriharyi.skillsail.dto.ProjectDto;

import java.util.List;

public interface ProjectService {

    ProjectDto createProject(ProjectDto projectDto);

    public List<ProjectDto> getAllProjects();

    ProjectDto getProjectById(String id);

    ProjectDto updateProject(String id, ProjectDto projectDto);

    void deleteProject(String id);
}
