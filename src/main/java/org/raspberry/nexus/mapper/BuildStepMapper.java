package org.raspberry.nexus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.raspberry.framework.core.mapper.GenericMapper;
import org.raspberry.nexus.dto.BuildStepDto;
import org.raspberry.nexus.entity.BuildStep;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BuildStepMapper extends GenericMapper<BuildStep, BuildStepDto> {

    @Mapping(target = "pipelineStepId", source = "pipelineStep.id")
    @Mapping(target = "buildId", source = "build.id")
    BuildStepDto toDto(BuildStep entity);

}