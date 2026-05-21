package org.raspberry.nexus.controller;

import lombok.RequiredArgsConstructor;
import org.raspberry.nexus.dto.ProjectDto;
import org.raspberry.nexus.service.ProjectService;
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
public class ProjectController {

    private final ProjectService service;

    @GetMapping("/projects/{id}")
    public ProjectDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/projects")
    public List<ProjectDto> findAll(@SortDefault(sort = "id") Sort sort) {
        return service.findAll(sort);
    }

    @PostMapping("/projects")
    public ProjectDto create(@RequestBody ProjectDto projectDto) {
        return service.create(projectDto);
    }

    @PutMapping("/projects/{id}")
    public ProjectDto update(@PathVariable Long id, @RequestBody ProjectDto projectDto) {
        return service.update(id, projectDto);
    }

    @DeleteMapping("/projects/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}