package org.raspberry.nexus.dto;

import lombok.Builder;

@Builder
public record ProjectDto(

        Long id,

        String name

) { }
