package org.raspberry.nexus.service;

import lombok.RequiredArgsConstructor;
import org.raspberry.nexus.dto.BuildDto;
import org.raspberry.nexus.entity.Build;
import org.raspberry.nexus.exception.ConflictException;
import org.raspberry.nexus.exception.NotFoundException;
import org.raspberry.nexus.mapper.BuildMapper;
import org.raspberry.nexus.repository.BuildRepository;
import org.raspberry.nexus.repository.BuildStepRepository;
import org.raspberry.nexus.utils.WorkspaceUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildService {

    private final BuildRepository buildRepository;
    private final BuildStepRepository buildStepRepository;

    private final BuildMapper mapper;

    public BuildDto findById(Long id) {
        Build build = buildRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Build with id '%s' not found", id));

        return mapper.toDto(build);
    }

    public List<BuildDto> findAll(Sort sort) {
        List<Build> buildList = buildRepository.findAll(sort);

        return mapper.toDto(buildList);
    }

    public List<BuildDto> findAllByPipelineId(Long pipelineId, Sort sort) {
        List<Build> buildList = buildRepository.findAllByPipelineId(pipelineId, sort);

        return mapper.toDto(buildList);
    }

    public void delete(Long id) {
        Build build = buildRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Build with id '%s' not found", id));

        if (!buildStepRepository.findAllByBuildId(id).isEmpty()) {
            throw new ConflictException("Build with id '%s' cannot be deleted because it is used by existing BuildSteps", id);
        }

        WorkspaceUtils.deleteWorkspace(build);

        buildRepository.delete(build);
    }

}