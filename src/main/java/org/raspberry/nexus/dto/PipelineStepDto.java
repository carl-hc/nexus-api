package org.raspberry.nexus.dto;

import lombok.Builder;

@Builder
public record PipelineStepDto(

        Long id,

        Long pipelineId,

        String name,

        Long order,

        String command

) { }
