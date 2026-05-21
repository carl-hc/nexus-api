package org.raspberry.nexus.controller;

import lombok.RequiredArgsConstructor;
import org.raspberry.nexus.dto.BuildDto;
import org.raspberry.nexus.service.BuildService;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BuildController {

    private final BuildService service;

    @GetMapping("/builds/{id}")
    public BuildDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/builds")
    public List<BuildDto> findAll(@SortDefault(sort = "id") Sort sort) {
        return service.findAll(sort);
    }

    @GetMapping("/pipelines/{pipelineId}/builds")
    public List<BuildDto> findAllByPipelineId(@PathVariable Long pipelineId, @SortDefault(sort = "id") Sort sort) {
        return service.findAllByPipelineId(pipelineId, sort);
    }

    @DeleteMapping("/builds/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}