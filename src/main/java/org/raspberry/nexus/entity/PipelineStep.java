package org.raspberry.nexus.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "PIPELINE_STEP")
public class PipelineStep {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PIPELINE_ID")
    private Pipeline pipeline;

    @Column(name = "STEP_NAME")
    private String name;

    @Column(name = "STEP_ORDER")
    private Long order;

    @Column(name = "STEP_COMMAND")
    private String command;

}