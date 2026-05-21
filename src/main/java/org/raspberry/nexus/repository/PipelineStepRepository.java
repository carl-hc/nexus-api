package org.raspberry.nexus.repository;

import org.raspberry.framework.core.repository.GenericRepository;
import org.raspberry.nexus.entity.PipelineStep;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PipelineStepRepository extends GenericRepository<PipelineStep, Long> {

    List<PipelineStep> findAllByPipelineId(Long pipelineId);

    List<PipelineStep> findAllByPipelineId(Long pipelineId, Sort sort);

}
