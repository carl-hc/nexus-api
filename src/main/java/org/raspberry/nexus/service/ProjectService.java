package org.raspberry.nexus.service;

import lombok.RequiredArgsConstructor;
import org.raspberry.nexus.dto.ProjectDto;
import org.raspberry.nexus.entity.Project;
import org.raspberry.nexus.exception.ConflictException;
import org.raspberry.nexus.exception.NotFoundException;
import org.raspberry.nexus.mapper.ProjectMapper;
import org.raspberry.nexus.repository.PipelineRepository;
import org.raspberry.nexus.repository.ProjectParamRepository;
import org.raspberry.nexus.repository.ProjectRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final PipelineRepository pipelineRepository;
    private final ProjectParamRepository projectParamRepository;
    private final ProjectRepository projectRepository;

    private final ProjectMapper mapper;

    public ProjectDto findById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project with id '%s' not found", id));

        return mapper.toDto(project);
    }

    public List<ProjectDto> findAll(Sort sort) {
        List<Project> projectList = projectRepository.findAll(sort);

        return mapper.toDto(projectList);
    }

    public ProjectDto create(ProjectDto projectDto) {
        Project project = new Project();
        project.setName(projectDto.name());

        project = projectRepository.save(project);

        return mapper.toDto(project);
    }

    public ProjectDto update(Long id, ProjectDto projectDto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project with id '%s' not found", id));

        project.setName(projectDto.name());

        project = projectRepository.save(project);

        return mapper.toDto(project);
    }

    public void delete(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project with id '%s' not found", id));

        if (!pipelineRepository.findAllByProjectId(id).isEmpty()) {
            throw new ConflictException("Project with id '%s' cannot be deleted because it is used by existing Pipelines", id);
        }

        if (!projectParamRepository.findAllByProjectId(id).isEmpty()) {
            throw new ConflictException("Project with id '%s' cannot be deleted because it is used by existing ProjectParams", id);
        }

        projectRepository.delete(project);
    }

}