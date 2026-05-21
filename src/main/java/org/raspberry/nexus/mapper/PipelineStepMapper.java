package org.raspberry.nexus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.raspberry.framework.core.mapper.GenericMapper;
import org.raspberry.nexus.dto.PipelineStepDto;
import org.raspberry.nexus.entity.PipelineStep;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PipelineStepMapper extends GenericMapper<PipelineStep, PipelineStepDto> {

    @Mapping(target = "pipelineId", source = "pipeline.id")
    PipelineStepDto toDto(PipelineStep entity);

}