package org.raspberry.nexus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.raspberry.framework.core.mapper.GenericMapper;
import org.raspberry.nexus.dto.BuildDto;
import org.raspberry.nexus.entity.Build;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BuildMapper extends GenericMapper<Build, BuildDto> {

    @Mapping(target = "pipelineId", source = "pipeline.id")
    BuildDto toDto(Build entity);

}