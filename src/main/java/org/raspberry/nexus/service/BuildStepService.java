package org.raspberry.nexus.service;

import lombok.RequiredArgsConstructor;
import org.raspberry.nexus.dto.BuildStepDto;
import org.raspberry.nexus.entity.BuildStep;
import org.raspberry.nexus.exception.NotFoundException;
import org.raspberry.nexus.mapper.BuildStepMapper;
import org.raspberry.nexus.repository.BuildStepRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildStepService {

    private final BuildStepRepository buildStepRepository;

    private final BuildStepMapper mapper;

    public BuildStepDto findById(Long id) {
        BuildStep buildStep = buildStepRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BuildStep with id '%s' not found", id));

        return mapper.toDto(buildStep);
    }

    public List<BuildStepDto> findAll(Sort sort) {
        List<BuildStep> buildStepList = buildStepRepository.findAll(sort);

        return mapper.toDto(buildStepList);
    }

    public List<BuildStepDto> findAllByBuildId(Long buildId, Sort sort) {
        List<BuildStep> buildStepList = buildStepRepository.findAllByBuildId(buildId, sort);

        return mapper.toDto(buildStepList);
    }

    public void delete(Long id) {
        BuildStep buildStep = buildStepRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BuildStep with id '%s' not found", id));

        buildStepRepository.delete(buildStep);
    }

}