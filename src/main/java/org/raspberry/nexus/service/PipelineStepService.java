package org.raspberry.nexus.service;

import lombok.RequiredArgsConstructor;
import org.raspberry.nexus.dto.PipelineStepDto;
import org.raspberry.nexus.entity.PipelineStep;
import org.raspberry.nexus.exception.ConflictException;
import org.raspberry.nexus.exception.NotFoundException;
import org.raspberry.nexus.mapper.PipelineStepMapper;
import org.raspberry.nexus.repository.BuildStepRepository;
import org.raspberry.nexus.repository.PipelineRepository;
import org.raspberry.nexus.repository.PipelineStepRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PipelineStepService {

    private final BuildStepRepository buildStepRepository;
    private final PipelineRepository pipelineRepository;
    private final PipelineStepRepository pipelineStepRepository;

    private final PipelineStepMapper mapper;

    public PipelineStepDto findById(Long id) {
        PipelineStep pipelineStep = pipelineStepRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("PipelineStep with id '%s' not found", id));

        return mapper.toDto(pipelineStep);
    }

    public List<PipelineStepDto> findAll(Sort sort) {
        List<PipelineStep> pipelineStepList = pipelineStepRepository.findAll(sort);

        return mapper.toDto(pipelineStepList);
    }

    public List<PipelineStepDto> findAllByPipelineId(Long pipelineId, Sort sort) {
        List<PipelineStep> pipelineStepList = pipelineStepRepository.findAllByPipelineId(pipelineId, sort);

        return mapper.toDto(pipelineStepList);
    }

    public PipelineStepDto create(PipelineStepDto pipelineStepDto) {
        PipelineStep pipelineStep = new PipelineStep();
        pipelineStep.setPipeline(pipelineRepository.getReferenceById(pipelineStepDto.pipelineId()));
        pipelineStep.setName(pipelineStepDto.name());
        pipelineStep.setOrder(pipelineStepDto.order());
        pipelineStep.setCommand(pipelineStepDto.command());

        pipelineStep = pipelineStepRepository.save(pipelineStep);

        return mapper.toDto(pipelineStep);
    }

    public PipelineStepDto update(Long id, PipelineStepDto pipelineStepDto) {
        PipelineStep pipelineStep = pipelineStepRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("PipelineStep with id '%s' not found", id));

        pipelineStep.setPipeline(pipelineRepository.getReferenceById(pipelineStepDto.pipelineId()));
        pipelineStep.setName(pipelineStepDto.name());
        pipelineStep.setOrder(pipelineStepDto.order());
        pipelineStep.setCommand(pipelineStepDto.command());

        pipelineStep = pipelineStepRepository.save(pipelineStep);

        return mapper.toDto(pipelineStep);
    }

    public void delete(Long id) {
        PipelineStep pipelineStep = pipelineStepRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("PipelineStep with id '%s' not found", id));

        if (!buildStepRepository.findAllByPipelineStepId(id).isEmpty()) {
            throw new ConflictException("PipelineStep with id '%s' cannot be deleted because it is used by existing BuildSteps", id);
        }

        pipelineStepRepository.delete(pipelineStep);
    }

}