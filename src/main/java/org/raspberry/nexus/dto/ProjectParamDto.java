package org.raspberry.nexus.dto;

import lombok.Builder;

@Builder
public record ProjectParamDto(

        Long id,

        Long projectId,

        String name,

        String value

) { }
