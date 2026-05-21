package org.raspberry.nexus.repository;

import org.raspberry.framework.core.repository.GenericRepository;
import org.raspberry.nexus.entity.Build;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildRepository extends GenericRepository<Build, Long> {

    List<Build> findAllByPipelineId(Long pipelineId);

    List<Build> findAllByPipelineId(Long pipelineId, Sort sort);

}
