package org.raspberry.nexus.repository;

import org.raspberry.framework.core.repository.GenericRepository;
import org.raspberry.nexus.entity.Pipeline;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PipelineRepository extends GenericRepository<Pipeline, Long> {

    List<Pipeline> findAllByProjectId(Long projectId);

    List<Pipeline> findAllByProjectId(Long projectId, Sort sort);

}
