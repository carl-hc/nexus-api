package org.raspberry.nexus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.raspberry.framework.core.mapper.GenericMapper;
import org.raspberry.nexus.dto.ProjectParamDto;
import org.raspberry.nexus.entity.ProjectParam;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectParamMapper extends GenericMapper<ProjectParam, ProjectParamDto> {

    @Mapping(target = "projectId", source = "project.id")
    ProjectParamDto toDto(ProjectParam entity);

}