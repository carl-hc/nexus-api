package org.raspberry.nexus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.raspberry.framework.core.mapper.GenericMapper;
import org.raspberry.nexus.dto.ProjectDto;
import org.raspberry.nexus.entity.Project;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper extends GenericMapper<Project, ProjectDto> {

}