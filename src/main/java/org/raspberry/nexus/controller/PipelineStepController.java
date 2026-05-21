package org.raspberry.nexus.controller;

import lombok.RequiredArgsConstructor;
import org.raspberry.nexus.dto.PipelineStepDto;
import org.raspberry.nexus.service.PipelineStepService;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PipelineStepController {

    private final PipelineStepService service;

    @GetMapping("/pipelineSteps/{id}")
    public PipelineStepDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/pipelineSteps")
    public List<PipelineStepDto> findAll(@SortDefault(sort = "id") Sort sort) {
        return service.findAll(sort);
    }

    @GetMapping("/pipelines/{pipelineId}/pipelineSteps")
    public List<PipelineStepDto> findAllByPipelineId(@PathVariable Long pipelineId, @SortDefault(sort = "id") Sort sort) {
        return service.findAllByPipelineId(pipelineId, sort);
    }

    @PostMapping("/pipelineSteps")
    public PipelineStepDto create(@RequestBody PipelineStepDto pipelineStepDto) {
        return service.create(pipelineStepDto);
    }

    @PutMapping("/pipelineSteps/{id}")
    public PipelineStepDto update(@PathVariable Long id, @RequestBody PipelineStepDto pipelineStepDto) {
        return service.update(id, pipelineStepDto);
    }

    @DeleteMapping("/pipelineSteps/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}