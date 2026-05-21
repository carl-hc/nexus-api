package org.raspberry.nexus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.raspberry.framework.core.mapper.GenericMapper;
import org.raspberry.nexus.dto.PipelineDto;
import org.raspberry.nexus.entity.Pipeline;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PipelineMapper extends GenericMapper<Pipeline, PipelineDto> {

    @Mapping(target = "projectId", source = "project.id")
    PipelineDto toDto(Pipeline entity);

}