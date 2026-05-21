package org.raspberry.nexus.repository;

import org.raspberry.framework.core.repository.GenericRepository;
import org.raspberry.nexus.entity.ProjectParam;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectParamRepository extends GenericRepository<ProjectParam, Long> {

    List<ProjectParam> findAllByProjectId(Long projectId);

    List<ProjectParam> findAllByProjectId(Long projectId, Sort sort);

}
