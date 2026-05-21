package org.raspberry.nexus.dto;

import lombok.Builder;
import org.raspberry.nexus.entity.BuildStatus;

import java.time.LocalDateTime;

@Builder
public record BuildStepDto(

        Long id,

        Long pipelineStepId,

        Long buildId,

        BuildStatus status,

        LocalDateTime iniProcess,

        LocalDateTime endProcess

) { }
