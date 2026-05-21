package org.raspberry.nexus.controller;

import lombok.RequiredArgsConstructor;
import org.raspberry.nexus.dto.PipelineDto;
import org.raspberry.nexus.service.PipelineService;
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
public class PipelineController {

    private final PipelineService service;

    @GetMapping("/pipelines/{id}")
    public PipelineDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/pipelines")
    public List<PipelineDto> findAll(@SortDefault(sort = "id") Sort sort) {
        return service.findAll(sort);
    }

    @GetMapping("/projects/{projectId}/pipelines")
    public List<PipelineDto> findAllByProjectId(@PathVariable Long projectId, @SortDefault(sort = "id") Sort sort) {
        return service.findAllByProjectId(projectId, sort);
    }

    @PostMapping("/pipelines")
    public PipelineDto create(@RequestBody PipelineDto pipelineDto) {
        return service.create(pipelineDto);
    }

    @PutMapping("/pipelines/{id}")
    public PipelineDto update(@PathVariable Long id, @RequestBody PipelineDto pipelineDto) {
        return service.update(id, pipelineDto);
    }

    @DeleteMapping("/pipelines/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/pipelines/{id}/execute")
    public void execute(@PathVariable Long id) {
        service.execute(id);
    }

}