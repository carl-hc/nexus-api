package org.raspberry.nexus.controller;

import lombok.RequiredArgsConstructor;
import org.raspberry.nexus.dto.BuildStepDto;
import org.raspberry.nexus.service.BuildStepService;
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
public class BuildStepController {

    private final BuildStepService service;

    @GetMapping("/buildSteps/{id}")
    public BuildStepDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/buildSteps")
    public List<BuildStepDto> findAll(@SortDefault(sort = "id") Sort sort) {
        return service.findAll(sort);
    }

    @GetMapping("/builds/{buildId}/buildSteps")
    public List<BuildStepDto> findAllByBuildId(@PathVariable Long buildId, @SortDefault(sort = "id") Sort sort) {
        return service.findAllByBuildId(buildId, sort);
    }

    @DeleteMapping("/buildSteps/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}