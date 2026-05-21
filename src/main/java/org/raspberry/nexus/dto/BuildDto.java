package org.raspberry.nexus.dto;

import lombok.Builder;
import org.raspberry.nexus.entity.BuildStatus;

import java.time.LocalDateTime;

@Builder
public record BuildDto(

        Long id,

        Long pipelineId,

        BuildStatus status,

        LocalDateTime iniProcess,

        LocalDateTime endProcess

) { }
