package org.raspberry.nexus.controller;

import lombok.RequiredArgsConstructor;
import org.raspberry.nexus.dto.ProjectParamDto;
import org.raspberry.nexus.service.ProjectParamService;
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
public class ProjectParamController {

    private final ProjectParamService service;

    @GetMapping("/projectParams/{id}")
    public ProjectParamDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/projectParams")
    public List<ProjectParamDto> findAll(@SortDefault(sort = "id") Sort sort) {
        return service.findAll(sort);
    }

    @GetMapping("/projects/{projectId}/projectParams")
    public List<ProjectParamDto> findAllByProjectId(@PathVariable Long projectId, @SortDefault(sort = "id") Sort sort) {
        return service.findAllByProjectId(projectId, sort);
    }

    @PostMapping("/projectParams")
    public ProjectParamDto create(@RequestBody ProjectParamDto projectParamDto) {
        return service.create(projectParamDto);
    }

    @PutMapping("/projectParams/{id}")
    public ProjectParamDto update(@PathVariable Long id, @RequestBody ProjectParamDto projectParamDto) {
        return service.update(id, projectParamDto);
    }

    @DeleteMapping("/projectParams/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}