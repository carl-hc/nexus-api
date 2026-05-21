package org.raspberry.nexus.service;

import lombok.RequiredArgsConstructor;
import org.raspberry.nexus.dto.PipelineDto;
import org.raspberry.nexus.entity.Pipeline;
import org.raspberry.nexus.entity.Project;
import org.raspberry.nexus.entity.ProjectParam;
import org.raspberry.nexus.exception.ConflictException;
import org.raspberry.nexus.exception.NotFoundException;
import org.raspberry.nexus.executor.BuildExecutor;
import org.raspberry.nexus.mapper.PipelineMapper;
import org.raspberry.nexus.repository.BuildRepository;
import org.raspberry.nexus.repository.PipelineRepository;
import org.raspberry.nexus.repository.PipelineStepRepository;
import org.raspberry.nexus.repository.ProjectParamRepository;
import org.raspberry.nexus.repository.ProjectRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PipelineService {

    private final BuildRepository buildRepository;
    private final PipelineRepository pipelineRepository;
    private final PipelineStepRepository pipelineStepRepository;
    private final ProjectRepository projectRepository;
    private final ProjectParamRepository projectParamRepository;

    private final BuildExecutor buildExecutor;

    private final PipelineMapper mapper;

    public PipelineDto findById(Long id) {
        Pipeline pipeline = pipelineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pipeline with id '%s' not found", id));

        return mapper.toDto(pipeline);
    }

    public List<PipelineDto> findAll(Sort sort) {
        List<Pipeline> pipelineList = pipelineRepository.findAll(sort);

        return mapper.toDto(pipelineList);
    }

    public List<PipelineDto> findAllByProjectId(Long projectId, Sort sort) {
        List<Pipeline> pipelineList = pipelineRepository.findAllByProjectId(projectId, sort);

        return mapper.toDto(pipelineList);
    }

    public PipelineDto create(PipelineDto pipelineDto) {
        Pipeline pipeline = new Pipeline();
        pipeline.setProject(projectRepository.getReferenceById(pipelineDto.projectId()));
        pipeline.setName(pipelineDto.name());

        pipeline = pipelineRepository.save(pipeline);

        return mapper.toDto(pipeline);
    }

    public PipelineDto update(Long id, PipelineDto pipelineDto) {
        Pipeline pipeline = pipelineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pipeline with id '%s' not found", id));

        pipeline.setProject(projectRepository.getReferenceById(pipelineDto.projectId()));
        pipeline.setName(pipelineDto.name());

        pipeline = pipelineRepository.save(pipeline);

        return mapper.toDto(pipeline);
    }

    public void delete(Long id) {
        Pipeline pipeline = pipelineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pipeline with id '%s' not found", id));

        if (!buildRepository.findAllByPipelineId(id).isEmpty()) {
            throw new ConflictException("Pipeline with id '%s' cannot be deleted because it is used by existing Builds", id);
        }

        if (!pipelineStepRepository.findAllByPipelineId(id).isEmpty()) {
            throw new ConflictException("Pipeline with id '%s' cannot be deleted because it is used by existing PipelineSteps", id);
        }

        pipelineRepository.delete(pipeline);
    }

    public void execute(Long id) {
        Pipeline pipeline = pipelineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pipeline with id '%s' not found", id));

        Map<String, String> params = new HashMap<>();

        Project project = pipeline.getProject();
        if (project != null) {
            for (ProjectParam projectParam : projectParamRepository.findAllByProjectId(project.getId())) {
                params.put(projectParam.getName(), projectParam.getValue());
            }
        }

        buildExecutor.execute(pipeline, params);
    }

}