package org.raspberry.nexus.service;

import lombok.RequiredArgsConstructor;
import org.raspberry.nexus.dto.ProjectParamDto;
import org.raspberry.nexus.entity.ProjectParam;
import org.raspberry.nexus.exception.NotFoundException;
import org.raspberry.nexus.mapper.ProjectParamMapper;
import org.raspberry.nexus.repository.ProjectParamRepository;
import org.raspberry.nexus.repository.ProjectRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectParamService {

    private final ProjectParamRepository projectParamRepository;
    private final ProjectRepository projectRepository;

    private final ProjectParamMapper mapper;

    public ProjectParamDto findById(Long id) {
        ProjectParam projectParam = projectParamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ProjectParam with id '%s' not found", id));

        return mapper.toDto(projectParam);
    }

    public List<ProjectParamDto> findAll(Sort sort) {
        List<ProjectParam> projectParamList = projectParamRepository.findAll(sort);

        return mapper.toDto(projectParamList);
    }

    public List<ProjectParamDto> findAllByProjectId(Long projectId, Sort sort) {
        List<ProjectParam> projectParamList = projectParamRepository.findAllByProjectId(projectId, sort);

        return mapper.toDto(projectParamList);
    }

    public ProjectParamDto create(ProjectParamDto projectParamDto) {
        ProjectParam projectParam = new ProjectParam();
        projectParam.setProject(projectRepository.getReferenceById(projectParamDto.projectId()));
        projectParam.setName(projectParamDto.name());
        projectParam.setValue(projectParamDto.value());

        projectParam = projectParamRepository.save(projectParam);

        return mapper.toDto(projectParam);
    }

    public ProjectParamDto update(Long id, ProjectParamDto projectParamDto) {
        ProjectParam projectParam = projectParamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ProjectParam with id '%s' not found", id));

        projectParam.setProject(projectRepository.getReferenceById(projectParamDto.projectId()));
        projectParam.setName(projectParamDto.name());
        projectParam.setValue(projectParamDto.value());

        projectParam = projectParamRepository.save(projectParam);

        return mapper.toDto(projectParam);
    }

    public void delete(Long id) {
        ProjectParam projectParam = projectParamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ProjectParam with id '%s' not found", id));

        projectParamRepository.delete(projectParam);
    }

}