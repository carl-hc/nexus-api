package org.raspberry.nexus.dto;

import lombok.Builder;

@Builder
public record PipelineDto(

        Long id,

        Long projectId,

        String name

) { }
