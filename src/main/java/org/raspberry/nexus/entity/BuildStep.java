package org.raspberry.nexus.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BUILD_STEP")
public class BuildStep {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PIPELINE_STEP_ID")
    private PipelineStep pipelineStep;

    @ManyToOne
    @JoinColumn(name = "BUILD_ID")
    private Build build;

    @Column(name = "STEP_STATUS")
    @Enumerated(EnumType.STRING)
    private BuildStatus status;

    @Column(name = "INI_PROCESS")
    private LocalDateTime iniProcess;

    @Column(name = "END_PROCESS")
    private LocalDateTime endProcess;

}