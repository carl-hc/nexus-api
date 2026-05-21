package org.raspberry.nexus.repository;

import org.raspberry.framework.core.repository.GenericRepository;
import org.raspberry.nexus.entity.BuildStep;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildStepRepository extends GenericRepository<BuildStep, Long> {

    List<BuildStep> findAllByPipelineStepId(Long pipelineStepId);

    List<BuildStep> findAllByPipelineStepId(Long pipelineStepId, Sort sort);

    List<BuildStep> findAllByBuildId(Long buildId);

    List<BuildStep> findAllByBuildId(Long buildId, Sort sort);

}
